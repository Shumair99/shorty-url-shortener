const form = document.getElementById('url-form');
const urlInput = document.getElementById('url-input');
const slugInput = document.getElementById('slug-input');
const expInput = document.getElementById('exp-input');

const message = document.getElementById('message');
const result = document.getElementById('result');
const shortLink = document.getElementById('short-link');
const targetURL = document.getElementById('target-url');
const expDate = document.getElementById('exp-date');

function showMessage(text, isError = false) {
  message.textContent = text;
  message.className = 'msg ' + (isError ? 'error' : 'success');
}

form.addEventListener('submit', async (e) => {
  e.preventDefault();
  message.textContent = '';
  result.style.display = 'none';

  const payload = {
    targetURL: urlInput.value.trim(),
  };

  const customSlug = slugInput.value.trim();
  if (customSlug) payload.customSlug = customSlug;

  const expirationDate = expInput.value.trim();
  if (expirationDate) payload.expirationDate = expirationDate;

  try {
    const res = await fetch('/api/links', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    if (res.status === 201) {
      const data = await res.json();
      showMessage('Short URL created!', false);
      shortLink.href = data.customURL;
      shortLink.textContent = data.customURL;
      targetURL.textContent = data.targetURL;
      expDate.textContent = data.expirationDate || 'none';
      result.style.display = 'block';
      return;
    }

    // Non-201: show error content if available
    let errorText = 'Something went wrong.';
    try {
      const err = await res.json();
      if (err && (err.message || err.error)) {
        errorText = err.message || err.error;
      }
    } catch (_) {
      errorText = await res.text();
    }
    showMessage(errorText || `HTTP ${res.status}`, true);

  } catch (err) {
    showMessage('Network error: ' + err.message, true);
  }
});
