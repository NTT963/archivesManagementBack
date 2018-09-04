var Main = {
        data () {
            return {
//				modal: false,
                noticeColumns: [
                    {
                        title: '编号',
                        key: 'iD'
                    },
                    {
                        title: '通知标题',
                        key: 'title'
                    },
                    {
                        title: '通知内容',
                        key: 'content'
                    },
					{
                        title: '显示优先级',
                        key: 'noticeOrder'
                    },
                    {
                        title: 'Action',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.show(params.index)
                                        }
                                    }
                                }, 'View'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.remove(params.index)
                                        }
                                    }
                                }, 'Delete')
                            ]);
                        }
                    }
                ],
                noticeData: []
            }
        },
		mounted: function () {
            // 向后台发送请求所有节点数据，并对data初始化
            callAxiosGetNoParams("/archivesManagementBack/getNotice.do?role=1", this.moutedSuccess, this.moutedFailed)
        },
        methods: {
        	addNotice(){
        		this.modal = true
        	},
			moutedSuccess(data) {
                this.noticeData = data
            },

            moutedFailed(data) {

            },
            
            show (index) {
                this.$Modal.info({
                    title: 'User Info',
                    content: `Name：${this.data6[index].name}<br>Age：${this.data6[index].age}<br>Address：${this.data6[index].address}`
                })
            },
            remove (index) {
                this.data6.splice(index, 1);
            }
        }
    }

var Component = Vue.extend(Main)
new Component().$mount('#app1')
