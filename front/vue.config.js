// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true,
//   lintOnSave:false,
//   devServer: {
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8080',
//         changeOrigin: true,
//         pathRewrite: {
//           '^/api': ''
//         }
//       }
//     }
//   }
// })
module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:9080',
        changeOrigin: true,
        pathRewrite: {
          '^/api:': ''
        }
      },
      '/upload': {
        target: 'http://211.62.99.58:9082',
        changeOrigin: true,
        pathRewrite: {
          '^/upload': '/api/upload'
        }
      }
    }
  }
}