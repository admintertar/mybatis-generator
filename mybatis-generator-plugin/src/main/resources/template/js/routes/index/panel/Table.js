import React from 'react'
import {Link, hashHistory} from 'dva/router'
import Utils from '../../../../utils/index';
import { message, Menu} from 'antd'
import {Iconfont} from '../../../../components';
import CommonTablePanel from "../../../../components/Panel/Table";
import * as terminalService from 'services/terminalorder';
import Display from '../../../../components/Display'

class TablePanel extends CommonTablePanel {

    //跳转需要修改
    renderRowActions(record, props) {
        const MenuProps = {
            style: {width: 140},
            onClick: ({key}) => {
                switch (key) {
                    case 'edit':
                        hashHistory.push({
                            pathname: '/inoutregist/edit',
                            query: {
                                dsId: record.dsId
                            }
                        });
                        break;
                    case 'detail':
                        hashHistory.push({
                            pathname: '/inoutregist/detail',
                            query: {
                                dsId: record.dsId
                            }
                        });
                        break;
                }
            }
        };

        let actions = [
            {key: 'edit', icon: 'edit', label: '修改'},
            {key: 'detail', icon: 'eye', label: '详情'},
        ];

        return <Menu {...MenuProps}>
            {actions.filter(Utils.filterPermission).map((item) => {
                return <Menu.Item  key={item.key}><Iconfont type={item.icon}/> <span>{item.label}</span></Menu.Item>;
            })}
        </Menu>;
    };

    //渲染每行的每个单元格的时候，选则要显示什么
    renderColumnConfig(config, props) {
        switch (config['key']) {
            case 'carNo':
                config.render = (value, record) => {
                    return <Link to={{
                        pathname: '/inoutregist/detail',
                        query: {
                            dsId: record.dsId
                        }
                    }}>{value}</Link>
                };
                break;
        }
        return config;
    };

}


export default TablePanel;
