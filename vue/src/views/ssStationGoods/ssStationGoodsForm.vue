<template>
  <a-modal
    :title="title"
    :width="1040"
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
        <a-form-item label="水站">
          <a-select v-decorator="['stationId', { rules: [{ required: true, message: '请选择水站!' }] }]"  @change="getGoodsInfoData">
            <a-select-option v-for="s in stationList" :key="s.id" :value="s.id">
              {{ s.station_name }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item label="选择商品">
          <a-select v-decorator="['goodsIdArray', { rules: [{ required: true, message: '请选择商品!' }] }]" mode="multiple" >
            <a-select-option v-for="g in goodsList" :key="g.id" :value="g.id">
              {{ g.goods_name }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <!-- <a-form-item label="商品">
         成本价：<a-input-number :min="1" v-decorator="['costPrice', {rules: [{required: false, message: '请输入数字！'}]}]" style="margin-right:20px;" disabled/>
         销售价：<a-input-number :min="1" v-decorator="['salePrice', {rules: [{required: false, message: '请输入数字！'}]}]" style="margin-right:20px;" disabled/>
         水站销售价：<a-input-number :min="1" v-decorator="['stationSalePrice', {rules: [{required: false, message: '请输入数字！'}]}]"/>
        </a-form-item> -->

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
    import { getWaterStationDict } from '@/api/ssWaterStation'
    import { getGoodsInfoDict } from '@/api/ssStationGoods'

    // 表单字段
    const fields = [
        'id',
        'stationId',
        'goodsId',
        'status',
        'goodsIdArray'
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
                stationList: [],
                goodsList: []
            }
        },
        created () {
            console.log('custom modal created')

            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
                this.model && this.form.setFieldsValue(pick(this.model, fields))
            })

            this.getWaterStationData()
            //this.getGoodsInfoData()
            this.stationList = []
            this.goodsList = []
        },
        methods: {
          getWaterStationData (params) {
              // 获取水站字典
              getWaterStationDict().then(res => {
                  if (res.code === 200) {
                      this.stationList = res.result
                  }
              })
          },
          getGoodsInfoData (value) {
            console.log("stationId=", value)
            var param = {
              stationId: value
            }
              // 获取商品字典信息
              getGoodsInfoDict(param).then(res => {  //排除水站已经添加的商品
                  if (res.code === 200) {
                      this.goodsList = res.result
                  }
              })
          }
        }
    }
</script>
