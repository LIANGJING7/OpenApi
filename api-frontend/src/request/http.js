import axios from 'axios'

// 创建 axios 请求实例
const httpService = axios.create({
  // 请求路径前缀
  baseURL: 'http://localhost:8888/api',
  // 请求超时时间
  timeout: 90000
})

// // 请求拦截
// httpService.interceptors.request.use(config => {
//   // 1. 这个位置就请求前最后的配置
//   // 2. 当然你也可以在这个位置 加入你的后端需要的用户授权信息(Token)
//   console.log(config)
//   return config
// }, error => {
//   // 请求错误时，一般不会出错，除非没有网络
//   return Promise.reject(error)
// })
//
// // 响应拦截
// httpService.interceptors.response.use(response => {
//   // 请求成功
//   // 1. 根据自己项目需求定制自己的拦截
//   // 2. 然后返回数据
//   console.log(response)
//   return response
// }, error => {
//   // 请求失败，这里的失败一般指服务器级别的失败，不是你自定义的ResultJson格式的
//   // ResultJson格式的数据算请求成功了，给你json数据了，需要到请求成功中去捕获处理
//   if (error && error.response) {
//     switch (error.response.status) {
//       case 400:
//         error.message = '错误请求'
//         break
//       case 401:
//         error.message = '未授权，请重新登录'
//         break
//       case 403:
//         error.message = '拒绝访问'
//         break
//       case 404:
//         error.message = '请求错误,未找到该资源'
//         break
//       case 405:
//         error.message = '请求方法未允许'
//         break
//       case 408:
//         error.message = '请求超时'
//         break
//       case 500:
//         error.message = '服务器端出错'
//         break
//       case 501:
//         error.message = '网络未实现'
//         break
//       case 502:
//         error.message = '网络错误'
//         break
//       case 503:
//         error.message = '服务不可用'
//         break
//       case 504:
//         error.message = '网络超时'
//         break
//       case 505:
//         error.message = 'http版本不支持该请求'
//         break
//       default:
//         error.message = `未知错误${error.response.status}`
//     }
//   } else {
//     error.message = '连接到服务器失败'
//   }
//   return Promise.reject(error)
// })

/**
 * 普通的get获取方式
 * url：请求地址
 * params ：请求参数
 * resolve：调用then时，传入的回调函数
 * reject：调用catch时，传入的回调函数
 * */
export function get (url, params = {}) {
  return new Promise((resolve, reject) => {
    httpService({
      url: url,
      method: 'get',
      params: params
    }).then(response => {
      resolve(response)
    }).catch(error => {
      reject(error)
    })
  })
}

/**
 * 使用get方式，下载文件
 * url：请求地址
 * params ：请求参数
 * resolve：调用then时，传入的回调函数
 * reject：调用catch时，传入的回调函数
 * */
// export function getBlob (url, params = {}) {
//   return new Promise((resolve, reject) => {
//     httpService({
//       url: url,
//       method: 'get',
//       params: params,
//       responseType: 'blob'
//     }).then(response => {
//       resolve(response)
//     }).catch(error => {
//       reject(error)
//     })
//   })
// }

/**
 * 使用get方式，下载文件后，需要调用该方法保存文件
 * res：请求成功的响应对象
 * fileName ：文件名
 * resolve：调用then时，传入的回调函数
 * reject：调用catch时，传入的回调函数
 * */
// export function saveBlob (res, fileName) {
//   let blob = new Blob([res.data])
//   let downloadElement = document.createElement('a')
//   // 创建下载的链接
//   let href = window.URL.createObjectURL(blob)
//   console.log(href)
//   downloadElement.style.display = 'none'
//   downloadElement.href = href
//   // 下载后文件名
//   downloadElement.download = fileName
//   document.body.appendChild(downloadElement)
//   // 点击下载
//   downloadElement.click()
//   // 下载完成移除元素
//   document.body.removeChild(downloadElement)
//   // 释放掉blob对象
//   window.URL.revokeObjectURL(href)
// }

/**
 * post提交Json格式的数据
 * */
export function postSendJson (url, params = {}) {
  return sendJson(url, 'post', params)
}

/**
 * post提交k=v&k=v格式的数据
 * */
// export function postSendForm (url, params = {}) {
//   return sendForm(url, 'post', params)
// }

/**
 * post提交单个文件
 * file是单个的文件对象，当你使用input标签上传多个文件时，
 * 需要在外部遍历，多次访问该方法
 * */
// export function postSendMultipart (url, key, file) {
//   return sendMultipart(url, key, file)
// }

/**
 * put提交Json格式的数据
 * */
export function putSendJson (url, params = {}) {
  return sendJson(url, 'put', params)
}

/**
 * put提交k=v&k=v格式的数据
 * */
// export function putSendForm (url, params = {}) {
//   return sendForm(url, 'put', params)
// }

/**
 * delete访问方式，并携带json格式数据
 * */
export function deleteSendJson (url, params = {}) {
  return sendJson(url, 'delete', params)
}
//
// /**
//  * delete访问方式，并携带k=v&k=v格式的数据
//  * */
// export function deleteSendForm (url, params = {}) {
//   return sendForm(url, 'delete', params)
// }

/**
 * 发送json格式的数据
 * url:请求地址
 * type:请求类型
 * params:请求参数对象
//  * */
function sendJson (url, type, params) {
  return new Promise((resolve, reject) => {
    httpService({
      url: url,
      method: type,
      data: JSON.stringify(params),
      headers: { 'Content-Type': 'application/json;charset=utf-8' }
    }).then(response => {
      resolve(response)
    }).catch(error => {
      reject(error)
    })
  })
}

// /**
//  * 发送form表单格式的数据
//  * url:请求地址
//  * type:请求类型
//  * params:请求参数对象
//  * */
// function sendForm (url, type, params) {
//   return new Promise((resolve, reject) => {
//     httpService({
//       url: url,
//       method: type,
//       data: params,
//       headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8' }
//     }).then(response => {
//       resolve(response)
//     }).catch(error => {
//       reject(error)
//     })
//   })
// }

// /**
//  * 发送单个文件，默认post方式
//  * 后端需要使用
//  * url:请求地址
//  * key: 后端获取文件时，请求参数的key值
//  * file:文件对象
//  * */
// function sendMultipart (url, key, file) {
//   let formData = new FormData()
//   if (file) {
//     formData.append(key, file)
//   }
//   return new Promise((resolve, reject) => {
//     httpService({
//       url: url,
//       method: 'post',
//       data: formData,
//       headers: { 'Content-Type': 'multipart/form-data' }
//     }).then(response => {
//       resolve(response)
//     }).catch(error => {
//       reject(error)
//     })
//   })
// }
