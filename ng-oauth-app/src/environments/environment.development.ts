// https://tonyxu-io.github.io/pkce-generator/
export const environment = {
  production: false,
  authorizeUri: 'http://localhost:9000/oauth2/authorize?',
  redirectUri : 'http://localhost:4200/authorized',
  clientId: 'ng-oidc-client',
  scope: 'openid profile',
  responseType: 'code',
  responseMode: 'form_post',
  codeChallengeMethod: 'S256',
  codeChallenge: 'MM4FV4tRVI2Ob8BfNFHjNlBgjMgPZJnWO58N3Jzq_Xc',
  codeVerifier: 'W3a9qZitvbRPgEVq2MZrAg078hdNQQOWrdv1lIc1KHY',
  grantType: "authorization_code",
  state: '1r1gilg083j',
  nonce: 'm1hi8yrgc1p',
  tokenEndpoint: "http://localhost:9000/oauth2/token"
};


