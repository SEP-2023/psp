// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  bank_front_url: 'http://localhost:4202',
  paypal_service_url:"http://localhost:8000/paypal-service",
  crypto_service_url:"http://localhost:8000/crypto-service",
  psp_backend:"http://localhost:8000/",
  agency_backend:"http://localhost:8085/",
  agency_front_url:"http://localhost:4201",
  psp_front_url:"http://localhost:4200",
  bank_url: "http://localhost:8086/",
  psp_auth_url: "http://localhost:8083/"

};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
