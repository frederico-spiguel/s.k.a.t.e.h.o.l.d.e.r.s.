// teste-registro-atividade.js (VERSÃO FINAL LIMPA - PARA GERAR CSV)

import http from 'k6/http';
import { check } from 'k6';

// OPÇÕES DO TESTE (Estresse com 700 usuários)
export const options = {
  stages: [
    { duration: '30s', target: 1000 },
    { duration: '1m', target: 2000 },
    { duration: '30s', target: 2000 },
    { duration: '1m', target: 2000 },
    { duration: '30s', target: 0 },
  ],
  thresholds: {
    'http_req_failed': ['rate<0.3'], // Permite até 30% de falha, já que é um teste de estresse
  },
};

// FUNÇÃO PRINCIPAL DO TESTE
export default function () {
  const API_BASE_URL = 'http://localhost:8080';
  
  // ATENÇÃO: Este token pode expirar. Se o teste falhar com erros (401/403),
  // significa que você precisa fazer login de novo e pegar um token novo.
  const AUTH_TOKEN = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJjb3NiYXJhdGEiLCJleHAiOjE3NTIwMjAxMzB9.FonKH9JRNCKqjA02mlwy68hx-ALd5SEUtuLZJUmoR-U';

  const payload = JSON.stringify({
    trickId: 8, // Usando um trickId válido
    obstaculo: 'teste_servico_1_estresse',
  });

  const params = {
    headers: {
      'Authorization': `Bearer ${AUTH_TOKEN}`,
      'Content-Type': 'application/json',
    },
  };

  const res = http.post(`${API_BASE_URL}/atividades`, payload, params);

  check(res, {
    'status foi 201': (r) => r.status === 201,
  });
  
  // Sem sleep para estresse máximo
}