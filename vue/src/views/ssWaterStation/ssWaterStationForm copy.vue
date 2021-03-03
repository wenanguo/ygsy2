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
        <a-form-item label="水站名称">
          <a-input v-decorator="['stationName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="负责人姓名">
          <a-input v-decorator="['leaderName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="手机号">
          <a-input v-decorator="['phone', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="座机">
          <a-input v-decorator="['landline', {rules: [{required: false, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <!-- 
        <a-form-item label="所在地市">
          <a-select v-decorator="['city', { rules: [{ required: true, message: '请选择地市!' }] }]" @change="handleCityChange">
            <a-select-option v-for="r in cityList" :key="r.areacode" :value="r.areacode">
              {{ r.areadesc }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="所在区县">
          <a-select v-decorator="['county', { rules: [{ required: true, message: '请选择区县!' }] }]" @change="handleCountyChange">
            <a-select-option v-for="r in countyList" :key="r.areacode" :value="r.areacode">
              {{ r.areadesc }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="所在乡镇">
          <a-select v-decorator="['town', { rules: [{ required: true, message: '请选择乡镇!' }] }]" >
            <a-select-option v-for="r in townList" :key="r.areacode" :value="r.areacode">
              {{ r.areadesc }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="水站详细地址">
          <a-input v-decorator="['address', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="水站位置">
          经度：<a-input-number :min="1" style="margin-right:10px" v-decorator="['latitude', {rules: [{required: true, message: '请输入经度！'}]}]" />
          维度：<a-input-number :min="1" v-decorator="['longitude', {rules: [{required: true, message: '请输入维度！'}]}]" />
        </a-form-item>
        -->
        <a-form-item>
          <div class="right-context" id="right-context">
                <baidu-map :style="{width:map.width,height:map.height}" class="map" ak="HIztk0ifyOeqlRxuO5KGc5tm" :zoom="map.zoom" :center="{lng: map.center.lng, lat: map.center.lat}" @ready="handler" :scroll-wheel-zoom="true">
                    <!--比例尺控件-->
                    <bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
                    <!--缩放控件-->
                    <bm-navigation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" ></bm-navigation>
                </baidu-map>
            </div>
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['status', { initialValue: 100 }]">
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="可用余额">
          <a-input-number :min="1" v-decorator="['availableBalance', {rules: [{required: false, message: '请输入经度！'}]}]" />
        </a-form-item>
        <a-form-item label="冻结余额">
          <a-input-number :min="1" v-decorator="['frozenBalance', {rules: [{required: false, message: '请输入经度！'}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'
    import { getRegionDict } from '@/api/ssWaterStation'
    import BaiduMap from 'vue-baidu-map/components/map/Map.vue'

    // 表单字段
    const fields = [
        'id',
        'stationName',
        'leaderName',
        'phone',
        'province',
        'city',
        'county',
        'town',
        'address',
        'latitude',
        'longitude',
        'status',
        'availableBalance',
        'frozenBalance'
    ]

    export default {
        components:{
          BaiduMap
        },
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
                provinceList: [],
                cityList: [],
                countyList: [],
                townList: [],
                map:{
                    center: {lng: 118.802422,lat:32.065631},//'南京市',
                    zoom: 12,
                    width:'1000px',
                    height:'710px'
                }
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

            this.provinceList = []
            this.cityList = []
            this.countyList = []
            this.townList = []
            this.getReionInfoList('GUIZ', 1) //页面初始化时查询贵州省下地市
        },
        methods: {
          getReionInfoList (value, level) {
              var params = {
                parentarea: value
              }
              // 获取区域字典
              getRegionDict(params).then(res => {
                  if (res.code === 200) {
                      if(level === 1){
                        this.cityList = res.result
                      }else if(level === 2){
                        this.countyList = res.result
                      }else if(level === 3){
                        this.townList = res.result
                      }else{
                        this.provinceList = res.result
                      }
                  }
              })
          },
          handleProvinceChange (v) {  //选择省份后联动地市
               this.getReionInfoList(v, 1);
          },
          handleCityChange (v) {     //选择地市后联动区县
              this.getReionInfoList(v, 2);
          },
          handleCountyChange (v) {   //选择区县后联动乡镇
             this.getReionInfoList(v, 3);
          }
          
        }
    }
</script>
