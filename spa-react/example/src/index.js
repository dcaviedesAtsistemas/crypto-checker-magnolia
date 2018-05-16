import React from 'react';
import ReactDOM from 'react-dom';

const MOUNT_NODE_APP1 = document.getElementById('app1');
const MOUNT_NODE_APP2 = document.getElementById('app2');
const MOUNT_NODE_APP3 = document.getElementById('app3');
const MOUNT_NODE_APP4 = document.getElementById('app4');

const render = () => {
    
    const App1 = require('./components/bitcoin').default;
    const App2 = require('./components/ethereum').default;
    const App3 = require('./components/cardano').default;
    const App4 = require('./components/litecoin').default;

    ReactDOM.render(<App1 />, MOUNT_NODE_APP1);

    ReactDOM.render(<App2 />, MOUNT_NODE_APP2);
    ReactDOM.render(<App3 />, MOUNT_NODE_APP3);
    ReactDOM.render(<App4 />, MOUNT_NODE_APP4);
};



render();

if (module.hot) {
    module.hot.accept(['./components/bitcoin'], () =>
                      setImmediate(() => {
        ReactDOM.unmountComponentAtNode(MOUNT_NODE_APP1);
        render();
    })
    );
    module.hot.accept(['./components/ethereum'], () =>
                      setImmediate(() => {
        ReactDOM.unmountComponentAtNode(MOUNT_NODE_APP2);
        render();
    })
    );
    module.hot.accept(['./components/cardano'], () =>
                      setImmediate(() => {
        ReactDOM.unmountComponentAtNode(MOUNT_NODE_APP3);
        render();
    })
    );
    module.hot.accept(['./components/litecoin'], () =>
                      setImmediate(() => {
        ReactDOM.unmountComponentAtNode(MOUNT_NODE_APP4);
        render();
    })
    );
}
