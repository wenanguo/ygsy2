<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="formCancel"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="model && model.id > 0" label="编号">
          <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="商品名称">
          <a-input v-decorator="['goodsName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="识别码">
          <a-input v-decorator="['goodsCode', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="商品类型">
          <a-select v-decorator="['goodsTypeId', { rules: [{ required: true, message: '请选择类型!' }] }]" @change="handleTypeChange" >
            <a-select-option v-for="type in typeList" :key="type.id" :value="type.id">
              {{ type.type_name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="水票信息" v-show="isShow">
          <a-select v-decorator="['ticketId', { rules: [{ required: checkType }] }]" style="width: 25em; margin-right: 10px;">
            <a-select-option v-for="ticket in ticketList" :key="ticket.id" :value="ticket.id">
              {{ ticket.ticket_name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="水票张数" v-show="isShow">
          <a-input-number :min="1" v-decorator="['ticketNum', {rules: [{required: checkType, message: '请输入数字！'}]}]" />
        </a-form-item>
        <a-form-item label="单位">
          <a-radio-group v-decorator="['unit', { rules: [{ required: true }], initialValue: '桶' }]">
            <a-radio :value="桶">桶</a-radio>
            <a-radio :value="张">张</a-radio>
            <a-radio :value="件">件</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="单价">
          <a-input-number :min="1" v-decorator="['unitPrice', {rules: [{required: true, message: '请输入数字！'}]}]" />
        </a-form-item>
        <a-form-item label="成本价">
          <a-input-number :min="1" v-decorator="['costPrice', {rules: [{required: true, message: '请输入数字！'}]}]" />
        </a-form-item>
        <a-form-item label="排序号">
          <a-input v-decorator="['sort', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="上下架">
          <a-radio-group v-decorator="['status', { rules: [{ required: true }], initialValue: 100 }]">
            <a-radio :value="100">已上架</a-radio>
            <a-radio :value="101">已下架</a-radio>
          </a-radio-group>
        </a-form-item>
        <!-- <a-form-item label="是否推荐">
          <a-radio-group v-decorator="['isrecommend', { rules: [{ required: true }], initialValue: 0 }]">
            <a-radio :value="0">否</a-radio>
            <a-radio :value="1">是</a-radio>
          </a-radio-group>
        </a-form-item> -->
        <a-form-item label="发布时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['publishTime', {rules: [{required: true}]}]" >
          </a-date-picker>
        </a-form-item>
        <a-form-item label="过期时间">
          <a-date-picker style="width: 100%" show-time v-decorator="['expiredTime', {rules: [{required: true}]}]" >
          </a-date-picker>
        </a-form-item>
        <a-form-item label="Tag">
          <a-select v-decorator="['tagIdArray', { rules: [{ required: false }] }]"  mode="multiple">
            <a-select-option v-for="tag in tagList" :key="tag.id" :value="tag.id">
              {{ tag.tag_name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="商品图片">
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
          <a-input v-decorator="['goodsImg', {initialValue: ''}]" type="hidden" />
        </a-form-item>
        <a-form-item label="商品详情">
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
          <quill-editor class="editor" v-model="description" ref="contentQuillEditor" :options="editorOption"></quill-editor>
          <a-input v-decorator="['description', {initialValue: ''}]" type="hidden" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'
    import { quillEditor } from 'vue-quill-editor'
    import quillConfig from '../../utils/quill-config.js'
    import 'quill/dist/quill.snow.css'
    import { getGoodsTypeDict } from '@/api/ssGoodsType'
    import { getTagDict } from '@/api/ssTagInfo'
    import { getTagIdList } from '@/api/ssGoodsTag'
    import { getTicketDict } from '@/api/ssWaterTicket'

    // 表单字段
    const fields = [
        'id',
        'goodsCode',
        'goodsName',
        'goodsTypeId',
        'ticketId',
        'ticketNum',
        'goodsImg',
        'unit',
        'unitPrice',
        'costPrice',
        'sort',
        'description',
        'status',
        'isrecommend',
        'publishTime',
        'expiredTime',
        'tagIdArray'
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
                typeList: [],
                isShow: false,
                checkType: false,
                tagList: [],
                tagIdArr: '',
                ticketList: [],
                description: '',
                editorOption: quillConfig
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

                  // 获取商品对应的标签，并赋值到多选框
                  this.getTagIdArrByGoodsId(this.model.id);

                  // 根据商品类型（水票固定为1）设置水票输入框是否显示、是否必填
                  if(this.model.goodsTypeId == 1) {
                    this.isShow = true
                    this.checkType = true
                  } else {
                    this.isShow = false
                    this.checkType = false
                  }

                  this.fileList = [{
                      uid: '-1',
                      name: this.model.goodsImg,
                      status: 'done',
                      url: this.model.goodsImg
                  }]

                  this.description = this.model.description
                }else{
                  this.description = ''
                  this.contentfileList = null
                  this.fileList = null
                  this.typeList = []
                  this.isShow=false
                  this.checkType=false
                  this.tagList = []
                  this.tagIdArr = ''
                  this.ticketList=[]
                }

            })

            // 当 富文本内容 发生改变时，为表单设置值
            this.$watch('description', () => {
               this.description && this.form.setFieldsValue({ description: this.description })
            })

            this.getGoodsTypeData();
            this.getTagInfoData();
            this.getTicketData();
        },
        methods: {
            formCancel () {
              // 解决打开引用bug
              this.description = ''
              this.$emit('cancel')
            },
            getGoodsTypeData (params) {
                // 获取商品类型
                getGoodsTypeDict().then(res => {
                    if (res.code === 200) {
                        this.typeList = res.result
                    }
                })
            },
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
                this.form.setFieldsValue({ goodsImg: info.file.response.result.url })
              }
            },
            handleTypeChange(value) {
              if(value == 1){
                this.isShow = true
                this.checkType = true
              }else {
                this.isShow = false
                this.checkType = false
              }
            },
           contentHandleChange (info) {
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
           },
            getTagInfoData (params) {
                // 获取Tag信息字典
                getTagDict().then(res => {
                    if (res.code === 200) {
                        this.tagList = res.result
                    }
                })
            },
            getTagIdArrByGoodsId(goodsId){
                getTagIdList({'goodsId': goodsId}).then(res => {
                  if (res.code === 200) {
                    this.tagIdArr = res.result
                  }
                  // console.log("tagIdArr：", this.tagIdArr)
                  this.form.setFieldsValue({ tagIdArray: this.tagIdArr })
                })
            },
            getTicketData (params) {
                // 获取水票字典信息
                getTicketDict().then(res => {
                    if (res.code === 200) {
                        this.ticketList = res.result
                    }
                })
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
