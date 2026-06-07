# ── Stage 1 — Build React ──────────────────────────────────────────────────────
FROM node:20-alpine AS build
WORKDIR /app

COPY essentia-frontend/package*.json ./
RUN npm ci

COPY essentia-frontend/ .
RUN npm run build

# ── Stage 2 — Serve con Nginx ─────────────────────────────────────────────────
FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html
COPY essentia-frontend/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
