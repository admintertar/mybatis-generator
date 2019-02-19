import React from 'react'
import {Link} from 'dva/router'
import {Input} from 'antd'
import {Permission,Panel} from 'components'
import Iconfont from '../../../../components/Iconfont'

class ToolsPanel extends Panel.Tools {

    renderFilter(filters){
        return (
            <Input.Search
                value={filters.fuzzyLookupStr || ''}
                onChange={(event) => this.setFilters({fuzzyLookupStr: event.target.value})}
                placeholder="车牌号/客户名称" style={{width: 200, marginRight: 15}}
                onSearch={this.handleFastSearch}
            />
        );
    };

    renderSelectedAction(){
        const sty = {marginRight: '13px'};
        return <span>
            <Permission.Button  style={sty} onClick={()=>this.props.onBatchAction('workorder')} className={'no-border'}><Iconfont type="trash" />转工单</Permission.Button>
        </span>
    }

    renderAction(){
        return <span>
            <Link to={{pathname:'/inoutregist/add'}}>
                <Permission.Button type="primary" permission="inoutregist:add">新增</Permission.Button>
            </Link>
        </span>
    }
}

export default ToolsPanel
