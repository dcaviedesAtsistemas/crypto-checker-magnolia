class SearchForm extends React.Component {
  
	constructor(props) {
    super(props);
    this.state = {value: '', characters: [], numFilmNames: [] }
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    
    this.graph = new Chart(document.getElementById("line-chart"), {
		  type: 'line',
		  data: {
		    labels: [1500,1600,1700,1750,1800,1850,1900,1950,1999,2050],
		    datasets: [{ 
		        data: [86,114,106,106,107,111,133,221,783,2478],
		        label: "Africa",
		        borderColor: "#3e95cd",
		        fill: false
		      }, { 
		        data: [282,350,411,502,635,809,947,1402,3700,5267],
		        label: "Asia",
		        borderColor: "#8e5ea2",
		        fill: false
		      }, { 
		        data: [168,170,178,190,203,276,408,547,675,734],
		        label: "Europe",
		        borderColor: "#3cba9f",
		        fill: false
		      }, { 
		        data: [40,20,10,16,24,38,74,167,508,784],
		        label: "Latin America",
		        borderColor: "#e8c3b9",
		        fill: false
		      }, { 
		        data: [6,3,2,2,7,26,82,172,312,433],
		        label: "North America",
		        borderColor: "#c45850",
		        fill: false
		      }
		    ]
		  },
		  options: {
		    title: {
		      display: true,
		      text: 'World population per region (in millions)'
		    }
		  }
		});
  }

  handleChange(event) {
    this.setState({value: event.target.value, characters: [], numFilmNames: [], eventType: event.type});
  }

  handleSubmit(event) {
    fetch('https://swapi.co/api/people/?search=' + this.state.value)
		  .then((response) => {
			return response.json();
		  })
		  .then((charactersReceived) => {
			this.getNumFilmNames(charactersReceived);
			this.getFilmNames(charactersReceived);
		  })
    event.preventDefault();
  }

  shouldComponentUpdate(nextProps, nextState){
  	if (nextState.eventType == 'change' || nextState.numFilmNames.length > 0){
  		return true;
  	}
  	return false;
  }

  getNumFilmNames(charactersReceived) {
		var numFilmNames = 0;
	
		var charactersJson = charactersReceived;
		var numCharactersFound = charactersJson.results.length;
		for(var i = 0; i < numCharactersFound; i++) {
			
			var numFilms = charactersJson.results[i].films.length;
			numFilmNames = numFilmNames + numFilms;
		}
		
		this.setState({ numFilmNames: numFilmNames })
   }
	
   getFilmNames(charactersReceived) {
		var charactersJson = charactersReceived;
		var numCharactersFound = charactersJson.results.length;
		for(var i = 0; i < numCharactersFound; i++) {
			
			var numFilms = charactersJson.results[i].films.length;
			for(var j = 0; j < numFilms; j++) {
			
				this.setFilmName(charactersJson, i, j);
			}
		}
	}
	
	setFilmName(charactersJson, characterPosition, filmPosition) {
		var title;
		var filmUrl = charactersJson.results[characterPosition].films[filmPosition];
		fetch(filmUrl)
		.then((response) => {
			var responseFromServer = response.json();
			return responseFromServer
		})
		.then((filmDetails) => {
			title = filmDetails.title;
			if(! charactersJson.results[characterPosition].filmsNames) {
				charactersJson.results[characterPosition].filmsNames = [];
			}
			charactersJson.results[characterPosition].filmsNames.push(title);
			this.setState({ characters: charactersJson, numFilmNames: this.state.numFilmNames - 1 });
		})
		
	}
	
	setChart() {
		
			

			
	}
	
	


  render() {

		var ret = [];

		ret.push(
				
			<div id="formulario">
			<canvas id="line-chart" width="800" height="450"></canvas>
		    </div>
			    
		);



		return React.DOM.div({}, ret);
  }
}

ReactDOM.render(<SearchForm />, document.getElementById('mount-point'));