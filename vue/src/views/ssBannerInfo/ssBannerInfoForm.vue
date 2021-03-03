<template>
  <a-modal
    :title="title"
    :width="1000"
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
        <a-form-item label="banner名称">
          <a-input v-decorator="['bannerName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="使用界面">
          <a-radio-group v-decorator="['useFlag', { initialValue: 1 }]" >
            <a-radio :value="1">用户端</a-radio>
            <a-radio :value="2">水站端</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="排序号">
          <a-input-number :min="1" v-decorator="['sort', {rules: [{required: true, message: '请输入数字！'}]}]" />
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['status', { initialValue: 100 }]">
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="失效时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['expiredTime', {rules: [{required: true}]}]" >
          </a-date-picker>
        </a-form-item>
        <a-form-item label="banner类型">
          <a-radio-group v-decorator="['bannerType', { initialValue: 1 }]" @change="handleTypeChange">
            <a-radio :value="1">
              外链
            </a-radio>
            <a-radio :value="2">
              商品
            </a-radio>
            <a-radio :value="3">
              富文本
            </a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="外链URL" v-show="linkShow">
          <a-input v-decorator="['linkUrl', {rules: [{required: linkRequired, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="商品" v-show="goodsShow">
          <a-select v-decorator="['goodsId', { rules: [{ required: goodsRequired, message: '请选择商品!' }] }]" >
            <a-select-option v-for="g in goodsList" :key="g.id" :value="g.id">
              {{ g.goods_name }}
            </a-select-option>
          </a-select>
        </a-form-item>
         <a-form-item label="banner内容" v-show="contentShow">
           <a-upload
             class="uploadImage"
             :multiple="false"
             list-type="picture"
             :file-list="contentfileList"
             action="/api/tencent/upload"
             @change="contentHandleChange"
             >
             <a-button> <a-icon type="upload" />上传图片</a-button>
           </a-upload>
           <quill-editor class="editor" v-model="bannerContent" ref="contentQuillEditor" :options="editorOption"></quill-editor>
           <a-input v-decorator="['bannerContent', {initialValue: ''}]" type="hidden" />
         </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'
    import { getGoodsInfoDict } from '@/api/ssGoodsInfo'
    import { quillEditor } from 'vue-quill-editor'
    import quillConfig from '../../utils/quill-config.js'

    // 表单字段
    const fields = [
        'id',
        'bannerName',
        'bannerImg',
        'bannerType',
        'bannerContent',
        'goodsId',
        'sort',
        'status',
        'expiredTime',
        'useFlag',
        'linkUrl'
    ]

    export default {
        components: { quillEditor },
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
                fileList: [],
                contentfileList: [],
                goodsList: [],
                linkRequired: true,
                goodsRequired: false,
                contentRequired: false,
                bannerContent: '',
                editorOption: quillConfig,
                linkShow: true,
                goodsShow: false,
                contentShow: false
            }
        },
        created () {
            console.log('custom modal created')

            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
                this.model && this.form.setFieldsValue(pick(this.model, fields))
                if(!this.model.expiredTime){
                  this.form.setFieldsValue({ expiredTime: null })
                }

                // 初始化图片上传
                if (this.model) {
                  this.fileList = [{
                    uid: '-1',
                    name: this.model.bannerImg,
                    status: 'done',
                    url: this.model.bannerImg
                  }]
                  this.bannerContent = this.model.bannerContent

                  var v = this.model.bannerType
                   if(v == 1){
                     this.linkShow=true
                     this.goodsShow=false
                     this.contentShow=false

                     this.linkRequired=true
                     this.goodsRequired=false
                     this.contentRequired=false
                   }else if(v == 2){
                     this.linkShow=false
                     this.goodsShow=true
                     this.contentShow=false

                     this.linkRequired=false
                     this.goodsRequired=true
                     this.contentRequired=false
                   }else if(v == 3){
                     this.linkShow=false
                     this.goodsShow=false
                     this.contentShow=true

                     this.linkRequired=false
                     this.goodsRequired=false
                     this.contentRequired=true
                   }
                } else {
                  this.fileList = null
                  this.contentfileList = null
                  this.goodsList = []
                  this.bannerContent = ''
                }
            })
            // 当 富文本内容 发生改变时，为表单设置值
            this.$watch('bannerContent', () => {
               this.bannerContent && this.form.setFieldsValue({ bannerContent: this.bannerContent })
            })
            this.getGoodsInfoData()
        },
        methods: {
          handleChange (info) {   //banner图片上传
            const fileListt = [...info.fileList]
            this.fileList = fileListt.slice(-1)
            if (info.file.status === 'uploading') {
              this.$emit('update:loading', true)
              return
            }
            if (info.file.status === 'done') {
              this.$emit('update:loading', false)
              this.form.setFieldsValue({ bannerImg: info.file.response.result.url })
            }
          },
          getGoodsInfoData (params) {
              // 获取商品字典信息
              getGoodsInfoDict().then(res => {
                  if (res.code === 200) {
                      this.goodsList = res.result
                  }
              })
          },
          handleTypeChange(e) {
            var v=e.target.value
            console.log(v)
            if(v == 1){
              this.linkShow=true
              this.goodsShow=false
              this.contentShow=false

              this.linkRequired=true
              this.goodsRequired=false
              this.contentRequired=false
            }else if(v == 2){
              this.linkShow=false
              this.goodsShow=true
              this.contentShow=false

              this.linkRequired=false
              this.goodsRequired=true
              this.contentRequired=false
            }else if(v == 3){
              this.linkShow=false
              this.goodsShow=false
              this.contentShow=true

              this.linkRequired=false
              this.goodsRequired=false
              this.contentRequired=true
            }
          },
         contentHandleChange (info) {  //富文本中的图片上传
           const contentfileListt = [...info.fileList]
           this.contentfileList = contentfileListt.slice(-1)
           if (info.file.status === 'uploading') {
             this.$emit('update:loading', true)
             return
           }
           if (info.file.status === 'done') {
             this.$emit('update:loading', false)
             const quill = this.$refs.contentQuillEditor.quill
             const length = quill.getSelection().index
             quill.insertEmbed(length, 'image', info.file.response.result.url)
             quill.setSelection(length + 1)
           }
         }
        }
    }
</script>
<style>
  .uploadImage{
      width: 0;
      height: 0;
      display: none;
  }
  .editor {
    height: 450px;
    overflow: hidden;
  }
  .ant-form-item-control{
    line-height:auto
  }
  .ql-container {
    height: calc(100% - 82px);
  }
  .ql-snow .ql-picker-label::before {
    float: left
   }
   .ql-snow .ql-color-picker .ql-picker-label svg, .ql-snow .ql-icon-picker .ql-picker-label svg {
    position: absolute;
    top: 0;
   }
   .ql-toolbar.ql-snow {
    padding: 0 !important;
   }
    .ql-formats {
     margin: 0 !important;
    }
   .ant-form-item-label {
    width: 20%;
   }
</style>
