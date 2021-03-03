<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="名称">
                <a-input v-model="queryParam.customerName" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="使用状态">
                <a-select v-model="queryParam.status" placeholder="请选择" default-value="0">
                  <a-select-option :value="0">全部</a-select-option>
                  <a-select-option :value="100">正常</a-select-option>
                  <a-select-option :value="101">禁用</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="resetSearchForm">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <div class="table-operator">
        <!-- <a-button type="primary" icon="plus" @click="handleAdd">新建</a-button> -->
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="1" @click="handleconfirmDel"><a-icon type="delete" />删除</a-menu-item>
            <!-- lock | unlock -->
            <a-menu-item key="2"><a-icon type="lock" />锁定</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作 <a-icon type="down" />
          </a-button>
        </a-dropdown>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="id"
        :columns="columns"
        :data="loadData"
        :alert="false"
        :rowSelection="rowSelection"
        showPagination="auto"
      >
       
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
         
        <span slot="pic" slot-scope="text, record">
          <img style="width:50px;heigth:50px" :src="record.customerImg" />
        </span>
        <span slot="status" slot-scope="text">
          <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
        </span>
        <span slot="address" slot-scope="text, record">
          <template>
            <a @click="handleLink(text,record,'address')">用户地址</a>
          </template>          
        </span>
        <span>
          <template>
            <a @click="handleLink(text,record,'ticket')">用户水票</a>
          </template>
        </span> 
        <span slot="ticketTotal" slot-scope="text, record">
          <template>
            <div v-if="text!=''">
              <a @click="handleLink(text,record,'ticket')">{{ text }}/张</a>
            </div>
            <div v-if="text==''">
              0/张
            </div>
          </template>
        </span>
        <span slot="totalPoints" slot-scope="text, record">
          <template>
            <div v-if="text!=''">
              <a @click="handleLink(text,record,'points')">{{ text }}</a>
            </div>
            <div v-if="text==''">
              0
            </div>
          </template>
        </span>
        <span slot="order" slot-scope="text, record">
          <template>
            <a @click="handleLink(text,record,'order')">用户订单</a>
          </template>
        </span>
        <span slot="orderTotal" slot-scope="text, record">
          <template>
            <div v-if="text!=''">
              <a @click="handleLink(text,record,'order')"> {{ text }}/单</a>
            </div>
            <div v-if="text==''">
              0/单
            </div>  
          </template> 
        </span> 
        <span slot="moneyTotal" slot-scope="text, record">
          <template>
            <a @click="handleLink(text,record,'#')"> {{ text }}/元</a>
          </template> 
        </span>        
        <span slot="action" slot-scope="text, record">
          <template>
            <div v-if="record.addressId!=''">
              <a @click="handleEdit(record)">水站分配</a>
            </div>
            <!-- <a-divider type="vertical" />
            <a-popconfirm title="是否要删除当前数据？" @confirm="handleDel(record)">
              <a style="color: red">删除</a>
            </a-popconfirm> -->
          </template>
        </span>
      </s-table>

      <edit-form
        ref="editForm"
        :title="title"
        :visible="visible"
        :loading="confirmLoading"
        :model="mdl"
        @cancel="handleCancel"
        @ok="handleOk"
      />
    </a-card>
  </page-header-wrapper>
</template>

<script>
    import moment from 'moment'
    import { STable, Ellipsis } from '@/components'
    import { statusMap } from '@/api/RC'
    import { getSsCustomerInfoList, saveSsCustomerInfo, delSsCustomerInfo, batchDelSsCustomerInfo } from '@/api/ssCustomerInfo'
    import { editStation } from '@/api/ssCustomerAddress'
    import EditForm from './ssCustomerInfoForm'

    const columns = [

         {
             title: '客户ID',
             sorter: true,
             dataIndex: 'id'
         },        
        {
            title: '客户名称',
            sorter: true,
            dataIndex: 'customerName'
        },        
        {
            title: '手机号',
            sorter: true,
            dataIndex: 'phone'
        },        {
            title: '客户头像',
            sorter: false,
            dataIndex: 'customerImg',
            scopedSlots: {customRender:'pic'}
        },        
        {
            title: '性别',
            sorter: true,
            dataIndex: 'sex',
            customRender:function (text) {
                if(text==1){
                    return '男'
                }else if(text==2){
                    return '女'
                }else if(text==0){
                    return '未知'
                }else{
                    return text
                }
            }
        }, 
        
        // {
        //     title: 'OPENID',
        //     sorter: true,
        //     dataIndex: 'openid'
        // },        
        // {
        //     title: '备注',
        //     sorter: true,
        //     dataIndex: 'memo'
        // },        
        // {
        //     title: '状态 1-正常，2-注销',
        //     sorter: true,
        //     width: '100px',
        //     scopedSlots: { customRender: 'status' },
        //     dataIndex: 'status'
        // },        
        {
            title: '创建时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'createTime'
        },    
         {
            title: '订单明细',
            sorter: true,
            dataIndex: 'orderTotal',
            scopedSlots: { customRender: 'orderTotal' }
            // customRender:function (text) {
            //   alert(text);
            //     if(text==undefined){
            //         return "0/单";                  
            //     }else{
            //         return text+"/单";
            //      // return  <a @click="handleLink(text,record,'ticket')">{text}/张</a>;
            //     }
            // }
         },         
          {
            title: '剩余水票',
            sorter: true,
            dataIndex: 'haveTicket',
            customRender:function (text) {
                if(text==''){
                    return '0/张'
                }else{
                    return text+'张'
                }
            }
         },    
        {
            title: '水票明细',
            sorter: true,
            dataIndex: 'ticketTotal',
            scopedSlots: { customRender: 'ticketTotal' }
            // customRender:function (text) {
            //     if(text==undefined){
            //         return "0/张";
            //     }else{
            //         return text+"/张";
            //     }
            // }
         },  
        {
            title: '积分总数',
            sorter: true,
            dataIndex: 'totalPoints',
            scopedSlots: { customRender: 'totalPoints' }
            // customRender:function (text) {
            //     if(text==undefined){
            //         return "0/张";
            //     }else{
            //         return text+"/张";
            //     }
            // }
         }, 
        {
            title: '总消费金额',
            sorter: true,
            dataIndex: 'moneyTotal',
            scopedSlots: { customRender: 'moneyTotal' },
            customRender:function (text) {
                if(text==''){
                    return '0元'
                }else{
                    return text+'元'
                }
            }
         },  
         {
            title: '用户地址',
            sorter: false,
            width: '150px',
            dataIndex: 'address',
        },   
        {
            title: '水站名称',
            sorter: false,
            width: '150px',
            dataIndex: 'stationName',
        }, 
        {
            title: '水站地址',
            sorter: false,
            width: '150px',
            dataIndex: 'waterStationAddress',
        }, 
        // {
        //     title: '更新时间',
        //     sorter: true,
        //     width: '150px',
        //     customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
        //     dataIndex: 'updateTime'
        // }, 
        // {
        //     title: '用户地址',
        //     sorter: false,
        //     width: '150px',
        //     dataIndex: 'address',
        //     scopedSlots: { customRender: 'address' }
        // },
        // {
        //     title: '用户水票',
        //     sorter: false,
        //     width: '150px',
        //     dataIndex: 'ticket',
        //     scopedSlots: { customRender: 'ticket' }
        // },
        // {
        //     title: '用户订单',
        //     sorter: false,
        //     width: '150px',
        //     dataIndex: 'order',
        //     scopedSlots: { customRender: 'order' }
        // },
        {
            title: '操作',
            dataIndex: 'action',
            width: '150px',
            scopedSlots: { customRender: 'action' }
        }
    ]



    export default {
        name: 'TableList',
        components: {
            STable,
            Ellipsis,
            EditForm
        },
        data () {
            this.columns = columns,
            this.uid = this.$route.params.customerId
            return {
                // create model
                visible: false,
                title: '新增',
                confirmLoading: false,
                mdl: null,
                // 高级搜索 展开/关闭
                advanced: false,
                // 查询参数
                queryParam: {},
                // 加载数据方法 必须为 Promise 对象
                loadData: parameter => {
                    const requestParameters = Object.assign({}, parameter, this.queryParam,this.$route.params)
                    // 删除为空字符串属性
                    Object.keys(requestParameters).forEach(item => {
                        if (!requestParameters[item]) delete requestParameters[item]
                    })
                    // 设置获取全部状态
                    if (requestParameters['status'] && requestParameters['status'] === 0) delete requestParameters['status']
                    console.log('loadData request parameters:', requestParameters)
                    return getSsCustomerInfoList(requestParameters)
                        .then(res => {
                            return res.result
                        })
                },
                selectedRowKeys: [],
                selectedRows: [],
                oldStationId:-1,
                addressId:-1
            }
        },
        filters: {
            statusFilter (type) {
                return statusMap[type].text
            },
            statusTypeFilter (type) {
                return statusMap[type].status
            }
        },
        computed: {
            rowSelection () {
                return {
                    selectedRowKeys: this.selectedRowKeys,
                    onChange: this.onSelectChange
                }
            }
        },
        methods: {
            handleAdd () {
                this.mdl = null
                this.title = '新增'
                this.visible = true
            },
            handleEdit (record) {
                this.title = '修改'
                this.visible = true
                this.addressId=record.addressId
                this.oldStationId=record.stationId
                console.log("oldinfo=",this.addressId,this.oldStationId)
                this.mdl = { ...record }
            },
             handleLink (text,record,whats) {
               // this.$router.push({ path: '/base/ss-customer-address' })
              if(whats=='address'){
                  this.$router.push({ name: 'SsCustomerAddressList', params: {customerId:record.customerId}})
              }else if(whats=='ticket'){
                  this.$router.push({ name: 'SsCustomerTicket', params: {customerId:record.customerId}})
              }else if(whats=='order'){
                  this.$router.push({ name: 'SsOrderInfoListById', params: {customerId:record.customerId}})
              }else if(whats=='points'){              
                 this.$router.push({ name: 'SsPaymentPoints', params: {customerId:record.customerId}})
              }
             // alert(record.customerId);
               
            },
            handleOk () {
                const form = this.$refs.editForm.form
                this.confirmLoading = true
                form.validateFields((errors, values) => {
                    if (!errors) {
                         // 日期格式化
                            values.createTime = moment(values.createTime).format('YYYY-MM-DD HH:mm:ss')
                            values.updateTime = moment(values.updateTime).format('YYYY-MM-DD HH:mm:ss')


                        if (values.id > 0) {
                            // 修改 e.g.

                            if(values.stationId!=this.oldStationId){//水站变更
                              var params = {
                                 addressId: this.addressId,
                                 stationId: values.stationId
                              }
                              editStation(params).then(res => {
                                this.visible = false
                                this.confirmLoading = false
                                // 重置表单数据
                                form.resetFields()
                                // 刷新表格
                                this.$refs.table.refresh()
                                this.$message.info('修改成功')
                              })
                            }else{//客户信息修改
                              saveSsCustomerInfo(values).then(res => {
                                  this.visible = false
                                  this.confirmLoading = false
                                  // 重置表单数据
                                  form.resetFields()
                                  // 刷新表格
                                  this.$refs.table.refresh()

                                  this.$message.info('修改成功')
                              })
                            }
                        } else {
                            // 新增
                            saveSsCustomerInfo(values).then(res => {
                                this.visible = false
                                this.confirmLoading = false
                                // 重置表单数据
                                form.resetFields()
                                // 刷新表格
                                this.$refs.table.refresh()

                                this.$message.info('新增成功')
                            })
                        }
                    } else {
                        this.confirmLoading = false
                    }
                })
            },
            handleconfirmDel () {
                this.$confirm({
                    title: '是否确认要删除选中数据?',
                    onOk: this.handleBatchDel,
                    class: '提示'
                })
            },
            handleBatchDel () {
                batchDelSsCustomerInfo(this.selectedRowKeys).then(res => {
                    this.confirmLoading = false
                    // 刷新表格
                    this.$refs.table.refresh()

                    this.$message.info('删除成功')
                })
            },
            handleDel (record) {
                if (record.id > 0) {

                  record.createTime = moment(record.createTime).format('YYYY-MM-DD HH:mm:ss')
                    // 修改 e.g.
                    delSsCustomerInfo(record).then(res => {
                        this.confirmLoading = false
                        // 刷新表格
                        this.$refs.table.refresh()

                        this.$message.info('删除成功')
                    })
                }
            },

            handleCancel () {
                this.visible = false

                const form = this.$refs.editForm.form
                form.resetFields() // 清理表单数据（可不做）
            },
            onSelectChange (selectedRowKeys, selectedRows) {
                this.selectedRowKeys = selectedRowKeys
                this.selectedRows = selectedRows
            },
            toggleAdvanced () {
                this.advanced = !this.advanced
            },
            resetSearchForm () {
                this.queryParam = {
                    date: moment(new Date())
                }
                // 刷新表格
                this.$refs.table.refresh()
            }
        }
    }
</script>
