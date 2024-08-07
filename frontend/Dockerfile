# Stage 1: Build the Angular application
FROM node:alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install
RUN npm install -g @angular/cli

COPY . .

RUN ng build --configuration=production

# Stage 2: Serve the Angular application
FROM nginx:stable-perl

COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80