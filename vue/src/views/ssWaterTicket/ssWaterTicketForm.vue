<template>
  <a-modal
    :title="title"
    :width="640"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="model && model.id > 0" label="编号">
          <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="水票编码">
          <a-input v-decorator="['ticketCode', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="水票名称">
          <a-input v-decorator="['ticketName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="水票类型">
          <a-radio-group v-decorator="['ticketType', { initialValue: 1 }]">
            <a-radio :value="1">瓶装</a-radio>
            <a-radio :value="2">桶装</a-radio>
          </a-radio-group>
          <!-- <a-input v-decorator="['ticketType', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" /> -->
        </a-form-item>
        <a-form-item label="水票图片">
          <!-- <a-input v-decorator="['ticketImg', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" /> -->
          <a-upload
            name="file"
            :multiple="false"
            list-type="picture"
            :file-list="fileList"
            @change="handleChange"
            action="/api/tencent/upload"
          >
            <a-button> <a-icon type="upload" />上传图片</a-button>
          </a-upload>
          <a-input v-decorator="['ticketImg', {initialValue: ''}]" type="hidden" />
        </a-form-item>
        <a-form-item label="过期时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['expiredTime', {rules: [{required: true}]}]" >
          </a-date-picker>
        </a-form-item>
        <!-- <a-form-item label="关联商品">
          <a-input v-decorator="['goodsId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item> -->
        <a-form-item label="水票价值">
          <a-input v-decorator="['ticketValue', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['status', { initialValue: 100 }]">
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'

    // 表单字段
    const fields = [
        'id',
        'ticketCode',
        'ticketName',
        'ticketType',
        'ticketImg',
        'expiredTime',
        'goodsId',
        'ticketValue',
        'status'
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
            this.formLayout = {
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 7 }
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 13 }
                }
            }
            return {
                form: this.$form.createForm(this),
                fileList: []
            }
        },
        created () {
            console.log('custom modal created')

            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
              if(this.model){
                
                this.model && this.form.setFieldsValue(pick(this.model, fields));

                 this.fileList = [{
                      uid: '-1',
                      name: this.model.ticketImg,
                      status: 'done',
                      url: this.model.ticketImg
                  }]
              }else{
                  this.fileList = null
              }
            })
        },
        methods: {
               handleChange (info) {
              // 图片上传
              const fileListt = [...info.fileList]
              this.fileList = fileListt.slice(-1)
              if (info.file.status === 'uploading') {
                this.$emit('update:loading', true)
                return
              }
              if (info.file.status === 'done') {
                // Get this url from response in real world.
                this.$emit('update:loading', false)
                this.form.setFieldsValue({ ticketImg: info.file.response.result.url })
              }
        }
    }}
</script>