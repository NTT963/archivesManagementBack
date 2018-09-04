//引入Axios
document.write("<script language='javascript' src='../js/axios.min.js'></script>");

/**
 * Axios带参数与回掉函数的Get请求
 * @param url 请求url路径
 * @param params 请求参数
 * @param funcSuc 成功回掉函数
 * @param funFail 失败回掉函数
 */
function callAxiosGet(url, params, funcSuc, funFail) {
	console.log("调用callAxiosGet请求")
    axios.get(url, {params: params}, {
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    })
        .then(function (response) {
            funcSuc(response.data)
        })
        .catch(function (error) {
            funFail(error.response)
        });
}


function callAxiosGetNoParams(url, funcSuc, funFail) {
    console.log("调用callAxiosGetNoParams请求")
    axios.get(url, {
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        }
    })
        .then(function (response) {
            //断言
            //成功回调
            funcSuc(response.data)
        })
        .catch(function (error) {
            // 失败回调
            //errorClass(error)
            funFail(error.response)
        });
}

/**
 * Axios只带请求url，无参数与回掉
 * @param url 请求url
 */
function callAxiosGetNoParamsAndFun(url) {
    axios.get(url, {
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        }
    })
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * Axios Get请求无回掉函数
 * @param url
 * @param params
 */

function callAxiosGetNoFun(url,params) {
    axios.get(url, {params: params}, {
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        }
    })
        .then(function (response) {
            console.log(response)
        })
        .catch(function (error) {
            console.log(error)
        });

}

/**
 * Axios 带参数与回掉函数的 Post请求
 * @param getUrl 请求链接
 * @param getParams 请求参数
 * @param funcSuc 请求成功的回调函数
 * @param funFail 请求失败的回调函数
 * @constructor
 */

function callAxiosPost(url, params, funcSuc, funFail) {
    axios.post(url, params)
        .then(function (response) {
            //成功回调函数
            funcSuc(response.data)
        })
        .catch(function (error) {
            //errorClass(error)
            //失败回调函数
            funFail(error.response)
        });
}

/**
 * Axios带参数，无回掉函数的 Post请求
 * @param url
 * @param domain
 */
function callAxiosPostNoFun(url, params) {
    axios.post(url, JSON.stringify(params), {
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    });
}

function errorClass(err) {
    if (err) {
        switch (err.response.status) {
            case 400:
                err.message = '错误请求'
                break;
            case 401:
                err.message = '未授权，请重新登录'
                break;
            case 403:
                err.message = '拒绝访问'
                break;
            case 404:
                err.message = '请求错误,未找到该资源'
                break;
            case 405:
                err.message = '请求方法未允许'
                break;
            case 408:
                err.message = '请求超时'
                break;
            case 500:
                err.message = '服务器端出错'
                break;
            case 501:
                err.message = '网络未实现'
                break;
            case 502:
                err.message = '网络错误'
                break;
            case 503:
                err.message = '服务不可用'
                break;
            case 504:
                err.message = '网络超时'
                break;
            case 505:
                err.message = 'http版本不支持该请求'
                break;
            default:
                err.message = `连接错误${err.response.status}`
        }
    } else {
        err.message = "连接到服务器失败"
    }
    return err.message
}
