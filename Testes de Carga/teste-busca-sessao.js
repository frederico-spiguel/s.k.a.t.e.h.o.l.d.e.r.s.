// teste-busca-sessao.js (versão limpa)
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 50 },
    { duration: '1m', target: 50 },
    { duration: '10s', target: 0 },
  ],
};
export default function () {
  const API_BASE_URL = 'http://localhost:8080';
  const AUTH_TOKEN = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYXJhdGFtYXJjb3MiLCJleHAiOjE3NTIwMjE1NTN9.e2E5qVA8U-xsFsfnSeE2cydTshKK4cE9sgBNeNjKWdQ';
  const DATA_DA_SESSAO_TESTE = '2025-07-05';
  const params = { headers: { 'Authorization': `Bearer ${AUTH_TOKEN}` } };
  const res = http.get(`${API_BASE_URL}/seshs/por-data?data=${DATA_DA_SESSAO_TESTE}`, params);
  check(res, { 'status foi 200': (r) => r.status === 200 });
  sleep(1);
}