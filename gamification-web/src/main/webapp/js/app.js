/**
 * Created by timur on 19.06.15.
 */
Array.prototype.remove = function (from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest)
};

angular
    .module('gamificationApp', ['ngFileUpload'])
    .constant('CONSTANTS', {
        API_URI_PREFIX: "/api/v1",
        TASK_URI: "/challenge"
    });