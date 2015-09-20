/**
 * 个人评分窗口
 * @date 20150919
 * @author wujl
 */

/**
 * 定义个人评分基础信息form
 */
var personalGradeForm = Ext.create("Ext.form.Panel", {
	title:"个人评分",
	region: "north",
    layout: 'form',
    bodyStyle :'padding:2px 30px 30px 0',
    border: false,
    items: [
            //第一行column三个从左到右
            {
			    layout : 'column',
			    border: false,
			    items:[
						{
						    columnWidth : .3, // 该列有整行中所占百分比
						    layout : "form", // 从上往下的布局
						    border: false,
						    items : [
						             {
							                id:'userName',
							                fieldLabel: '姓名',
							                name: 'respVo.userName',
							                xtype: "displayfield",
							                value:"马云"
						             	}
						             ]
						   },
							{
							    columnWidth : .3, // 该列有整行中所占百分比
							    layout : "form", // 从上往下的布局
							    border: false,
							    items : [
							             {
											    id:'sex',
											    fieldLabel: '性别',
											    name: 'respVo.sex',
											    xtype: "displayfield",
											    value:"男"
							             	}
							             ]
							   },
								{
								    columnWidth : .3, // 该列有整行中所占百分比
								    layout : "form", // 从上往下的布局
								    border: false,
								    items : [
								             {
												    id:'birth',
												    fieldLabel: '出生年月',
												    name: 'respVo.birth',
												    xtype: "displayfield",
												    value:"1978年10月"
								             	}
								             ]
								   }
			           ]
            },
            //第二行
            {
			    layout : 'column',
			    border: false,
			    items:[
						{
						    columnWidth : .3, // 该列有整行中所占百分比
						    layout : "form", // 从上往下的布局
						    border: false,
						    items : [
						             {
							                   id:'political',
							                    fieldLabel: '政治面貌',
							                    name: 'respVo.political',
							                    xtype: "displayfield",
							                    value:"中共党员"
						             	}
						             ]
						   },
							{
							    columnWidth : .3, // 该列有整行中所占百分比
							    layout : "form", // 从上往下的布局
							    border: false,
							    items : [
							             {
							                    id: 'schooling',
							                    fieldLabel: '学历',
							                    name: 'respVo.schooling',
							                    xtype: "displayfield",
							                    value:"大专"
							             	}
							             ]
							   },
								{
								    columnWidth : .3, // 该列有整行中所占百分比
								    layout : "form", // 从上往下的布局
								    border: false,
								    items : [
								             {
												    id: 'jobs',
												    fieldLabel: '现任岗位',
												    name: 'respVo.jobs',
												    xtype: "displayfield",
												    value:"执行总裁"
								             	}
								             ]
								   }
			           ]
            },
            //第三行
            {
			    layout : 'form',
			    border: false,
			    items:[
					{
					    id: 'problem',
					    fieldLabel: '存在问题',
					    name: 'respVo.problem',
					    xtype: "textarea",
					    height:90,
					    width:600,
		                maxLength:200,
		                allowBlank: false
					}
			      ]
            },
            //第四行
            {
			    layout : 'form',
			    border: false,
			    items:[
					{
					    id: 'workPlan',
					    fieldLabel: '明年工作计划',
					    name: 'respVo.workPlan',
					    xtype: "textarea",
					    height:90,
					    width:600,
		                maxLength:200,
		                allowBlank: false
					}
			      ]
            },
            //第五行
            {
			    layout : 'form',
			    border: false,
			    items:[
					{
					    id: 'compositeScores',
					    fieldLabel: '综合得分',
					    name: 'respVo.compositeScores',
					    xtype: "displayfield"
					}
			      ]
            }
            ]
});



