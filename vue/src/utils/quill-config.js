/*富文本编辑图片上传配置*/
const uploadConfig = {
  action:  '',  // 必填参数 图片上传地址
  methods: 'POST',  // 必填参数 图片上传方式
  token: '',  // 可选参数 如果需要token验证，假设你的token有存放在sessionStorage
  name: 'img',  // 必填参数 文件的参数名
  size: 500,  // 可选参数   图片大小，单位为Kb, 1M = 1024Kb
  accept: 'image/png, image/gif, image/jpeg, image/bmp, image/x-icon'  // 可选 可上传的图片格式
}

// toolbar工具栏的工具选项
const toolbarOptions = [          //工具菜单栏配置
  ['bold', 'italic', 'underline', 'strike'],     // 加粗 斜体 下划线 删除线
  ['blockquote', 'code-block'],                  // 引用  代码块
  [{ header: 1 }, { header: 2 }],                // 1、2 级标题
  [{ list: 'ordered' }, { list: 'bullet' }],     // 有序、无序列表
  // [{ script: "sub" }, { script: "super" }],   // 上标/下标
  // [{ indent: '-1' }, { indent: '+1' }],       // 缩进
  // [{'direction': 'rtl'}],                     // 文本方向
  [{ size: ['small', false, 'large', 'huge'] }], // 字体大小
  [{ header: [1, 2, 3, 4, 5, 6, false] }],       // 标题
  [{ color: [] }, { background: [] }],           // 字体颜色、字体背景颜色
  [{ font: [] }],                                // 字体种类
  [{ align: [] }],                               // 对齐方式
  ['link', 'image'],                             // ["link", "image", "video"]  链接、图片、视频
  ['clean']                                      // 清除文本格式
]

const handlers = {
  'image': (value) => {
    if (value) {
      document.querySelector('.uploadImage input').click()
    } else {
      this.$refs.contentQuillEditor.format('image', false)
    }
  }
}
export default {
  placeholder: '',
  theme: 'snow',  // 主题
  modules: {
    toolbar: {
      container: toolbarOptions,  // 工具栏选项
      handlers: handlers  // 事件重写
    }
  }
}