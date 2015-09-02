angular
    .module('gamificationApp')
    .config(PostRequestConfig);

PostRequestConfig.$inject = ['$httpProvider'];
function PostRequestConfig($httpProvider) {
    $httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
    $httpProvider.defaults.transformRequest.unshift(function (data, headersGetter) {
        if (headersGetter()["Content-Type"] == "application/json") {
            return data;
        }
        var key, result = [];
        for (key in data) {
            if (data.hasOwnProperty(key)) {
                result.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
            }
        }
        return result.join("&");
    });
}