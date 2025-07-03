// src/services/api.js
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

// Isso é um "interceptor": ele roda antes de TODA requisição
api.interceptors.request.use(async (config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;