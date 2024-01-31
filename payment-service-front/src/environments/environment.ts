const host = '192.168.0.15';

export const environment = {
  production: false,
  bank_front_url: `http://${host}:4202`,
  paypal_service_url: `http://${host}:8000/paypal-service`,
  crypto_service_url: `http://${host}:8000/crypto-service`,
  psp_backend: `http://${host}:8000/`,
  agency_backend: `http://${host}:8085/`,
  agency_front_url: `http://${host}:4201`,
  psp_front_url: `http://${host}:4200`,
  bank_url: `http://${host}:8086/`,
};
