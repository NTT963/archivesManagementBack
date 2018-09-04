var Main = {
        data () {
            return {
                value1: 0,
                banners:[]
            }
        },
		mounted: function () {
            callAxiosGetNoParams("/archivesManagementBack/getBannerPics.do", this.moutedSuccess, this.moutedFailed)
        },
        methods: {
        	moutedSuccess(data) {
        		console.log("查到的信息为==>"+JSON.stringify(data))
        		this.banners = data.banner
        	},

            moutedFailed(data) {
            	console.log("初始化失败")

            },
        	
        }
    }

var Component = Vue.extend(Main)
new Component().$mount('#app')