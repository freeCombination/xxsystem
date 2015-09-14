/**
  * @class MyGridView
  * @extends Ext.grid.GridView
  * 制作双层表头。
  * @param {String} mtext  表头名
  * @param {Nubmer} mcol   向后跨越子表头个数
  * @param {Nubmer} mwidth 上至下第一层表头的宽度,即父表头的宽度
*/
MyGridView = Ext.extend(Ext.grid.GridView, {
    constructor:function(){
        MyGridView.superclass.constructor.call(this, {
            templates : {
                header : new Ext.Template(
                        ' <table border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                        ' <thead> <tr class="x-grid3-hd-row">{mergecells} </tr>'
                                + ' <tr class="x-grid3-hd-row">{cells} </tr> </thead>',
                        " </table>"),
                mhcell : new Ext.Template(
                        ' <td class="x-grid3-header" colspan="{mcols}" style="width:{mwidth}px;"> <div align="center">{value}</div>',
                        " </td>")
            }
        });
    },
    renderHeaders : function() {
        var cm = this.cm, ts = this.templates;
        var ct = ts.hcell, ct2 = ts.mhcell;
        var cb = [], p = {}, mcb = [];
        
        for (var i = 0, len = cm.getColumnCount(); i < len; i++) {
            p['id']    = cm.getColumnId(i);
            p['value'] = cm.getColumnHeader(i) || "";
            p['style'] = this.getColumnStyle(i, true);
            
            if (cm.config[i].align == 'right') {
                p['istyle'] = 'padding-right:16px';
            }
            cb.push(ct.apply(p));
            
            if (cm.config[i].mtext){
                mcb.push(ct2.apply({
                    value : cm.config[i].mtext,
                    mcols : cm.config[i].mcol,
                    mwidth : cm.config[i].mwidth
                }));          
            }
        }
        return ts.header.apply({
            cells : cb.join(""),
            tstyle : 'width:' + this.getTotalWidth() + ';',
            mergecells : mcb.join("")
        });
    }
}); 
