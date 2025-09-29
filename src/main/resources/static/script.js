const form = document.getElementById('url-form');
const urlInput = document.getElementById('url-input');
const slugInput = document.getElementById('slug-input');
const expInput = document.getElementById('exp-input');

const message = document.getElementById('message');
const result = document.getElementById('result');
const shortLink = document.getElementById('short-link');
const targetURL = document.getElementById('target-url');
const expDate = document.getElementById('exp-date');

const submitBtn = document.getElementById('submit-btn');
const resetBtn = document.getElementById('reset-btn');
const copyBtn = document.getElementById('copy-btn');

function setLoading(isLoading){
  submitBtn.classList.toggle('loading', isLoading);
  submitBtn.disabled = isLoading;
}

function showMessage(text, type = 'success') {
  message.textContent = text;
  message.className = 'msg ' + (type === 'error' ? 'error' : 'success');
}

function clearMessage(){
  message.textContent = '';
  message.className = 'msg';
}

function isValidURL(val) {
  try { 
    const u = new URL(val);
    return !!u.protocol && !!u.host;
  } catch { return false; }
}

function toIsoOrNull(val) {
  if (!val) return null;
  const dt = new Date(val);
  // Convert local datetime-local to UTC ISO without milliseconds for backend consistency
  const iso = new Date(dt.getTime() - dt.getTimezoneOffset() * 60000)
                .toISOString().replace('.000Z', 'Z');
  return iso;
}

function revealResult(data){
  shortLink.href = data.customURL;
  shortLink.textContent = data.customURL;
  targetURL.textContent = data.targetURL;
  expDate.textContent = data.expirationDate || 'none';
  result.style.display = 'block';
}

form.addEventListener('submit', async (e) => {
  e.preventDefault();
  clearMessage();
  result.style.display = 'none';

  const target = urlInput.value.trim();
  if (!isValidURL(target)) {
    showMessage('Please enter a valid URL (include https://).', 'error');
    urlInput.focus();
    return;
  }

  const payload = { targetURL: target };
  const customSlug = slugInput.value.trim();
  if (customSlug) payload.customSlug = customSlug;

  const iso = toIsoOrNull(expInput.value.trim());
  if (iso) payload.expirationDate = iso;

  try {
    setLoading(true);

    const res = await fetch('/api/links', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    const contentType = res.headers.get('content-type') || '';
    const parseJSON = contentType.includes('application/json');

    if (res.status === 201) {
      const data = parseJSON ? await res.json() : {};
      showMessage('Short URL created!');
      revealResult(data);
      return;
    }

    //status != 201 - show error
    let errorText = `Something went wrong (HTTP ${res.status}).`;
    try {
      if (parseJSON) {
        const err = await res.json();
        if (err && (err.message || err.error)) errorText = err.message || err.error;
      } else {
        const raw = await res.text();
        if (raw) errorText = raw;
      }
    } catch(_) {}

    showMessage(errorText, 'error');

  } catch (err) {
    showMessage('Network error: ' + err.message, 'error');
  } finally {
    setLoading(false);
  }
});

resetBtn.addEventListener('click', () => {
  clearMessage();
  result.style.display = 'none';
});

copyBtn.addEventListener('click', async () => {
  const val = shortLink.textContent?.trim();
  if (!val) return;

  try{
    await navigator.clipboard.writeText(val);
    const original = copyBtn.textContent;
    copyBtn.textContent = 'Copied!';
    setTimeout(() => (copyBtn.textContent = original), 1200);
  }catch{
    // Fallback: select text via prompt
    window.prompt('Copy to clipboard:', val);
  }
});
