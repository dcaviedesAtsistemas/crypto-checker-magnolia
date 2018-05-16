import React from 'react';
import {Line} from 'react-chartjs-2';

const data = {
  labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
  datasets: [
    {//rgb(240, 139, 22)
      label: 'Bitcoin',
      fill: false,
      backgroundColor: 'rgba(240, 139, 22,0.4)',
      borderColor: 'rgba(240, 139, 22,1)',
      borderCapStyle: 'butt',
      borderDash: [],
      borderDashOffset: 0.0,
      borderJoinStyle: 'miter',
      pointBorderColor: 'rgba(250, 180, 22,1)',
      pointBackgroundColor: '#fff',
      pointBorderWidth:3,
      pointHoverRadius: 5,
      pointHoverBackgroundColor: 'rgba(240, 139, 22,1)',
      pointHoverBorderColor: 'rgba(220,220,220,1)',
      pointHoverBorderWidth: 2,
      pointRadius: 1,
      pointHitRadius: 10,
      data: [7000, 7000, 7000, 7000, 7000, 7000, 7000]
    }
  ]
};

const options = {
    scales: {
        yAxes: [{
            ticks: {
                beginAtZero:false,
                stepSize: 0.5
            }
        }]
    }
};

//ZERO DATA
// const data = {
//         labels: zero_array,
//         datasets:  [
//             {
//                 label: "DataSet #1", // Name of the line
//                 data: zero_array, // data to represent
//                 // The following makes the line way less ugly
//                 fillColor: "rgba(151,187,205,0.2)",
//                 strokeColor: "rgba(151,187,205,1)",
//                 pointColor: "rgba(151,187,205,1)",
//                 pointStrokeColor: "#fff",
//                 pointHighlightFill: "#fff",
//                 pointHighlightStroke: "rgba(151,187,205,1)"
//             }
//         ]
// };



function getRandomInt (min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

// function getJSONBit() {
//     fetch('https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR')
// 		  .then((response) => {
// 			return response.json();
//     }).catch(function(err) {
//       // Error :(
//     });
// }

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

var values = [getRandomInt(6900, 7000),getRandomInt(6900, 7000),getRandomInt(6900, 7000),getRandomInt(6900, 7000),getRandomInt(6900, 7000),getRandomInt(6900, 7000),getRandomInt(6900, 7000)];

function getRDataNext () {

  for (var i = 0; i < values.length; i++) {
      if(i<values.length-1){
        values[i] = values[i+1];
      }
  }

  getJSON('https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR',
      function(err, data) {
        if (err !== null) {
          console.log('Something went wrong: ' + err);
        } else {
          values[values.length] = data.EUR;
        }
      });

      return values[values.length];
}

const getState = () => ({
  labels: ['January', 'February', 'March', 'April', 'May', 'June'],
  datasets: [
    {
      label: 'My First dataset',
      fill: false,
      lineTension: 0.1,
      backgroundColor: 'rgba(75,192,192,0.4)',
      borderColor: 'rgba(75,192,192,1)',
      borderCapStyle: 'butt',
      borderDash: [],
      borderDashOffset: 0.0,
      borderJoinStyle: 'miter',
      pointBorderColor: 'rgba(75,192,192,1)',
      pointBackgroundColor: '#fff',
      pointBorderWidth: 1,
      pointHoverRadius: 5,
      pointHoverBackgroundColor: 'rgba(75,192,192,1)',
      pointHoverBorderColor: 'rgba(220,220,220,1)',
      pointHoverBorderWidth: 2,
      pointRadius: 1,
      pointHitRadius: 10,
      data: [values[0], values[1], values[2], values[3], values[4], values[5], getRDataNext()],
    }
  ]
});

export default React.createClass({
  displayName: 'LineExample',

  getInitialState() {
    return getState();
  },

  componentWillMount() {
    setInterval(() => {
      this.setState(getState());
    }, 15000 );
  },

  render() {
    return (
      <div>
        <h2>Line Example</h2>
        <Line
        data={this.state}
        options={options} />
      </div>
    );
  }
});
