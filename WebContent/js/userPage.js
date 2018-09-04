var Main = {
    data() {
        return {
            tab: 'ManageArchivePage.html',
            isActive: true,
            sideActive:{
                'MyArchive.html':false,
                'Search.html':false,
                'ManageArchivePage.html':false,
                'UploadArchivePage.html':false,
                'ArchiveModel.html':false,
                'ArchiveChart.html':false,
                'ClassifyTreePage.html':false,
                'WaitMeApprove.html':false,
                'UserManage.html':false,
            },
            userInfo:{
                userID:'',
                userName:''
            }

        }
    },
    mounted: function () {
        console.log("获取cook信息===========》" + this.getCookie("tab"))
        console.log("获取cook信息===========》" + this.getCookie("userID"))
        console.log("获取cook信息===========》" + this.getCookie("userName"))
        this.userInfo.userID = this.getCookie("userId")
        this.userInfo.userName = this.getCookie("userName")
        if (this.getCookie("tab") === null) {
            console.log("没有Cook信息")
        } else {
            console.log("===有Cook信息")
            this.tab = this.getCookie("tab")
        }
        // callAxiosGetNoParams("/archivesManagementBack/getBannerPics.do", this.moutedSuccess, this.moutedFailed)
    },
    methods: {
        changeIframeURL(url) {
            for (var prop in this.sideActive)
            {
                this.sideActive[prop] = false
            }
            this.tab = url
            this.setCookie("tab", url)
            this.sideActive[url] = true
            console.log("获取cook信息===========》" + this.getCookie("tab"))

        },

        setCookie(name, value) {
            var Days = 1;  //cookie 将被保存一年
            var exp = new Date(); //获得当前时间
            exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000); //换成毫秒
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString()
        },
        getCookie(name) {
            var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
            if (arr != null) {
                return unescape(arr[2]);
            } else {
                return null;
            }
        }
// moutedSuccess(data) {
        //     console.log("查到的信息为==>"+JSON.stringify(data))
        //     this.banners = data.banner
        // },
        //
        // moutedFailed(data) {
        //     console.log("初始化失败")
        //
        // },

    }
}

var Component = Vue.extend(Main)
new Component().$mount('#app')