var Main = {
    data() {
        return {
            tabs: [],
            tabsValue: '',
            menus: [
                {
                    submenuName: "wxPageAdmin",
                    submenuTitle: "微信小程序页面配置",
                    submenuIcon: "chatbubbles",
                    menuItem: [
                        {
                            name: "banner",
                            title: "轮播图管理",
                            page: "BannerPage.html"
                        },
                        {
                            name: "notice",
                            title: "系统通知管理",
                            page: "NoticePage.html"
                        }
                    ]

                }, {
                    submenuName: "userManage",
                    submenuTitle: "用户管理",
                    submenuIcon: "ios-people",
                    menuItem: [
                        {
                            name: "roleAndAuth",
                            title: "权限角色配置",
                            page: "AuthPage.html"
                        },
                        {
                            name: "userChart",
                            title: "用户报表",
                            page: "UserChartPage.html"
                        }
                    ]
                }, {
                    submenuName: "archiveManage",
                    submenuTitle: "档案管理",
                    submenuIcon: "archive",
                    menuItem: [
                        {
                            name: "classifyTree",
                            title: "目录配置",
                            page: "ClassifyTreePage.html"
                        },
                        {
                            name: "manageArchive",
                            title: "档案管理",
                            page: "ManageArchivePage.html"
                        },
                        {
                            name: "uploadArchive",
                            title: "上传档案",
                            page: "UploadArchivePage.html"
                        },
                        {
                            name: "search",
                            title: "档案检索",
                            page: "Search.html"
                        }
                    ]
                }, {
                    submenuName: "systemManage",
                    submenuTitle: "系统监控",
                    submenuIcon: "social-windows",
                    menuItem: [
                        {
                            name: "systemChart",
                            title: "系统报表",
                            page: "SystemChart.html"
                        }
                    ]
                }

            ]
        }
    },
    methods: {
        handleTabsCloseAll() {
            this.tabs.splice(0, this.tabs.length);
        },
        menuClick(name) {
            var tab = {}
            tab.key = name
            tab.label = "档案目录"
            console.log("点击了" + name)
            this.tabs.push(tab)
        },
        itemClick(item) {
            console.log("点击了" + item.name + item.title)
            var hasExit = false
            for (var i = 0, l = this.tabs.length; i < l; i++) {
                console.log('arr[%s]', i, JSON.stringify(this.tabs[i]))

                if (this.tabs[i].name == item.name) {
                    hasExit = true
                    console.log("该标签已经插入")
                    break
                }
            }
            if (!hasExit) {
                var tab = {}
                tab.name = item.name
                tab.key = item.page
                tab.label = item.title
                this.tabs.push(tab)
            }
            this.tabsValue = item.name

            console.log("tabs.length> 0" + (this.tabs.length > parseInt(0)))

        }
    }
}
var Component = Vue.extend(Main)
new Component().$mount('#app')
