<template>
    <a-modal
            :title="title"
            :width="640"
            :visible="visible"
            :confirmLoading="loading"
            @cancel="() => { $emit('cancel') }"
            @ok="() => { $emit('ok') }"
    >
        <a-spin :spinning="loading">
            <!-- <a-form :form="form" v-bind="formLayout">                                     
                     <a-form-item v-show="model && model.id > 0" label="编号">
                        <a-input v-decorator="['id', { initialValue: 0 }]"  />
                    </a-form-item> 
                     <a-form-item label="意见回复父id">
                        <a-input v-decorator="['parentId', { initialValue: 0 }]" disabled />
                    </a-form-item> 
                    <a-form-item label="意见内容">
                        <a-textarea v-decorator="['content', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>                    
                    <a-form-item label="商品id">
                        <a-input v-decorator="['goodsId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="用户id">
                        <a-input v-decorator="['customerId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="1-普通意见回复，2-商品评价">
                        <a-input v-decorator="['opinionType', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item> 
                    <a-form-item label="意见回复时间">
                        <a-date-picker style="width: 100%" show-time v-decorator="['createTime', {rules: [{required: true}]}]" >
                        </a-date-picker>
                    </a-form-item>
            </a-form> -->
            <a-table :columns="columns" :data-source="tableData" >
                  <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
            </a-table>
        </a-spin>
    </a-modal>
</template>

<script>
    import moment from 'moment'
    import { listReplyByCustomerId } from '@/api/ssOpinions'
    import pick from 'lodash.pick'
    
    // 表单字段
    const columns = [
        {
            title: '主键id',
            sorter: true,
            width: '80px',
            dataIndex: 'id'
        },        {
            title: '意见内容',
            sorter: true,
            dataIndex: 'content'
        },        
        {
            title: '意见回复父id',
            sorter: true,
            dataIndex: 'parentId'
        }, 
        {
            title: '回复状态',
            sorter: true,
            dataIndex: 'status',
             customRender:function (text) {
                if(text==1){
                    return '未回复'
                }else if(text==2){
                    return '已回复'
                }else{
                    
                    return text
                }
            }
        }
        // ,           
        // {
        //     title: '意见反馈时间',
        //     sorter: true,
        //     width: '150px',
        //     customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
        //     dataIndex: 'createTime'
        // }
        // ,
        // {
        //     title: '操作',
        //     dataIndex: 'action',
        //     width: '150px',
        //     scopedSlots: { customRender: 'action' }
        // }
    ]

    export default {
        props: {
            visible: {
                type: Boolean,
                required: true
            },
            title: {
                type: String,
                required: true
            },
            loading: {
                type: Boolean,
                default: () => false
            },
            model: {
                type: Object,
                default: () => null
            }
        },
        data () {
            this.columns = columns
             
            return {
                // create model
                tableData: [],
                title: '新增',
                confirmLoading: false,
                mdl: null,
                // 高级搜索 展开/关闭
                advanced: false,
                // 查询参数
                queryParam: {},
              
                // selectedRowKeys: [],
                // selectedRows: []
            }
        },
        created () {
            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))
        },
        watch: {
            model:{
                deep: true,
                handler() {
                    this.tableData = [];
                    //this.tableData.push(this.model);
                    this.loadData(this.model);
                }
            }
        },
        methods:{
              loadData(parameter){
                    // 加载数据方法 必须为 Promise 对象
                 
                    const requestParameters = Object.assign({}, parameter, this.queryParam)
                    // 删除为空字符串属性
                    Object.keys(requestParameters).forEach(item => {
                        if (!requestParameters[item]) delete requestParameters[item]
                    })
                 //     alert("00000000")
                    // 设置获取全部状态
                    if (requestParameters['status'] && requestParameters['status'] === 0) delete requestParameters['status']
                    console.log('loadData request parameters:', requestParameters)
                    return listReplyByCustomerId(requestParameters)
                        .then(res => {
                            return res.result
                        })
              }
        }
    }
</script>
