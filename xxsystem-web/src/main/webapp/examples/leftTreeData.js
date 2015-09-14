/**
 * 定义通用控件示例左侧树的数据
 * @author HEDJ
 */
var lefttreedata = {
	root : {
		text : "通用控件",
		expanded : true,
		children : [ {
			text : 'ExtJs通用控件',
			children:[{
				text:'表格(grid)',
				children:[{
					text : '表格面板(Ext.grid.Panel)',
					leaf : true,
					url : 'grid/panel.html'
				},{
					text : '单元格提示(Ext.grid.column.Column)',
					leaf : true,
					url : 'grid/column.html'
				}, {
					text : '按钮禁用(Ext.selection.CheckboxModel)',
					leaf : true,
					url : 'grid/checkboxModel.html'
				},  {
					text : '操作列按钮(Ext.grid.column.Action)',
					leaf : true,
					url : 'grid/columnAction.html'
				}, {
					text : '查询隐藏列(Ext.SearchBtn)',
					leaf : true,
					url : 'grid/searchBtn.html'
				}, {
					text : '禁用排序(Ext.grid.header.Container)',
					leaf : true,
					url : 'grid/container.html'
				},{
					text : '分页大小(Ext.data.Store)',
					leaf : true,
					url : 'grid/store.html'
				},{
					text : '分页信息(Ext.PagingToolbar)',
					leaf : true,
					url : 'grid/pagingToolbar.html'
				},{
					text : '数据格式(Ext.data.proxy.Ajax)',
					leaf : true,
					url : 'grid/ajax.html'
				} ]
			},
			{
				text : '表单(form)',
				children : [ {
					text : '必填项红色星号(Ext.form.field.Base)',
					leaf : true,
					url : 'form/base.html'
				},{
					text : '表单验证(Ext.form.field.VTypes)',
					leaf : true,
					url : 'form/vtype.html'
				},{
					text : '提交进度条(Ext.form.action.Action)',
					leaf : true,
					url : 'form/actionOverride.html'
				}]
			},{
				text : '消息提示(msg)',
				children : [ {
					text : '消息提示(Ext.Msg)',
					leaf : true,
					url:'msg/msg.html'
				}]
			},{
				text : '附件组(attach)',
				children : [ {
					text : '附件组(Ext.Attach)',
					leaf : true,
					url:'attach/attach.html'
				}]
			}]
		},{
			text : 'jQuery通用控件',
			leaf : false
		},{
			text : '自定义JS通用控件',
			leaf : false
		}]
	}
};