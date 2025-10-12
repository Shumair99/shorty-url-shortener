Future updates planned: Analytics such as visitor count, CD for automatic deployment
# 🔗 Shorty — Modern URL Shortener (Spring Boot + Docker + PostgreSQL)

> **A self-hosted, full-stack URL shortener** built with **Spring Boot**, **PostgreSQL**, and **Docker Compose**, deployed on a **Hetzner VPS** behind a **Caddy reverse proxy**.  
> Shorty provides a lightweight, scalable, and privacy-friendly alternative to third-party shorteners like Bitly — built from the ground up for performance and simplicity.

**Live Demo:** [https://shortyurl.live](https://shortyurl.live)

---

## Project Overview

**Shorty** is a production-ready URL shortening service built as a personal full-stack & DevOps project.  
It demonstrates the complete development lifecycle — from backend architecture and RESTful API design to containerization, CI/CD automation, and secure deployment.

The goal was to deepen my understanding of **Spring Boot microservices**, **database persistence**, and **modern cloud workflows**, while creating a tangible SaaS-style product.

---

## Tech Stack

| Layer | Technologies |
|-------|---------------|
| **Backend** | Java • Spring Boot • Spring Data JPA • PostgreSQL |
| **Frontend** | Thymeleaf (minimal) + Tailwind (optional UI layer) |
| **Database** | PostgreSQL (persistent volume) |
| **Deployment** | Docker Compose • Caddy (HTTPS reverse proxy) • Hetzner VPS |
| **CI** | GitHub Actions (build, test) |
| **Monitoring** | Docker health checks + logs |

---

## Features

- **Shorten URLs instantly** via REST or web form  
- **Custom slug generation** with collision prevention  
- **Click tracking and redirect metrics**  
- **Persistent storage** via PostgreSQL (volume-backed)  
- **HTTPS with automatic TLS certificates** (Caddy + Let’s Encrypt)  
- **Fully containerized** for portable deployments  
- **CI** for automated testing

---

## How It Works

1. A user submits a long URL via the web form or API.  
2. Shorty generates a **unique slug** (randomized or custom).  
3. The slug–URL pair is saved to the PostgreSQL database.  
4. When someone visits `https://shortyurl.live/{slug}`,  
   the app looks up the original URL and issues an HTTP 302 redirect.  

Each redirect increments the visit counter in the database, enabling analytics later on.

---

## Local Setup (Development)

### Clone and configure
```bash
git clone https://github.com/Shumair99/shorty-url-shortener
cd shorty
```
