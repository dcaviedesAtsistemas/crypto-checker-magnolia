import React from 'react';
import {Line} from 'react-chartjs-2';

const options = {
    scales: {
        yAxes: [{
            ticks: {

                display: false
            },
            gridLines: {
                    display:false,
                    drawBorder: false
                }
        }],
        xAxes: [{
            ticks: {
                display: false,
            },
            gridLines: {
                    display:false,
                    drawBorder: false
                }
        }]
    },
    legend: {
            display: false
         }
};

var getJSON = function(url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.responseType = 'json';
    xhr.onload = function() {
        var status = xhr.status;
        if (status === 200) {
            callback(null, xhr.response);
        } else {
            callback(status, xhr.response);
        }
    };
    xhr.send();
};

var values = [];
var eurValue = '';
var usdValue = '';
var highDayEUR = '';
var lowDayEUR = '';
var highDayUSD = '';
var lowDayUSD = '';
var totalVolume24H = '';


function getRDataNext () {

    getJSON('http://localhost:8080/magnolia-webapp/.rest/cryptoEndpoint?fullName=Litecoin',
            function(err, data) {
        if (err !== null) {
            console.log('Something went wrong: ' + err);
        } else {
            values = JSON.parse(data.results[0].values);
            eurValue = data.results[0].eurValue;
            usdValue = data.results[0].usdValue;
            highDayEUR = data.results[0].highDayEUR;
            lowDayEUR = data.results[0].lowDayEUR;
            highDayUSD = data.results[0].highDayUSD;
            lowDayUSD = data.results[0].lowDayUSD;
            totalVolume24H = data.results[0].totalVolume24H;
        }
    });

    return values;
}

const getState = () => ({
    labels: ['','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''],
    datasets: [
        {

            fill: true,
            lineTension: 0.1,
            backgroundColor: 'rgba(104, 150, 0,0.4)',
            borderColor: 'rgba(104, 150, 0,1)',
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: 'rgba(104, 150, 0,1)',
            pointBackgroundColor: '#fff',
            pointBorderWidth: 1,
            pointHoverRadius: 2,
            pointHoverBackgroundColor: 'rgba(2104, 150, 0,1)',
            pointHoverBorderColor: 'rgba(104, 150, 0,1)',
            pointHoverBorderWidth: 2,
            pointRadius: 1,
            pointHitRadius: 10,
            data: getRDataNext(),
        }
    ]
});

export default React.createClass({
    displayName: 'Litecoin',

    getInitialState() {
        return getState();
    },

    componentWillMount() {
        setInterval(() => {
            this.setState(getState());
        }, 3000 );
    },

    render() {
        return (

            <div className="col-sm-12">

            <div className="containerOurs">
            <div className="invoice">

            <header>
            <section>
            <h1>Litecoin
            <div style={{marginTop: '2%'}}>{eurValue} € - {usdValue} $</div></h1>
            </section>

            <section>
            </section>

            <section>
            <div>
                <Line data={this.state} options={options} />
            </div>
            </section>

            </header>

            <main>
              <section>
                <span></span>
                <span></span>
                <span></span>
              </section>
              <section>
              </section>
              <section>
                <span>Total volume 24H</span>
                <span>{totalVolume24H}</span>
                <span>Highday</span>
                <span>{highDayEUR} €</span>
                <span>Lowday</span>
                <span>{lowDayEUR} €</span>
                <span>Highday</span>
                <span>{highDayUSD} $</span>
                <span>Lowday</span>
                <span>{lowDayUSD} $</span>
              </section>
            </main>

            </div>
            </div>

            </div>


        );
    }
});
