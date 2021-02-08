<template>
  <div class="container">
    <div class="row mt-5">
      <div class="col">
        <h1 class="text-center">COVID-19 DATA for Germany</h1>
        <div class="current-numbers"> 
          
                {{ currentNumbers.date }}, Germany
          
           <div>{{ currentNumbers.cases }} cases.</div> 
          
         <div> {{ currentNumbers.deaths }} deaths,</div>  
          
          <div> {{ currentNumbers.recoveries }} recoveries.</div> 
          
        </div>
      </div>
    </div>
    <div class="row mt-5" v-if="arrPositive.length > 0">
      <div class="col">
        <h2 class="text-center">Positive</h2>
        <line-chart
          :chartData="arrPositive"
          :options="chartOptions"
          :chartColors="positiveChartColors"
          label="Positive"
        />
      </div>
    </div>

    <div class="row mt-5" v-if="arrHospitalized.length > 0">
      <div class="col">
        <h2 class="text-center">Hospitalized</h2>
        <line-chart
          :chartData="arrHospitalized"
          :options="chartOptions"
          :chartColors="hospitalizedChartColors"
          label="Hospitalized"
        />
      </div>
    </div>

    <div class="row mt-5" v-if="arrInIcu.length > 0">
      <div class="col">
        <h2 class="text-center">In ICU</h2>
        <line-chart
          :chartData="arrInIcu"
          :options="chartOptions"
          :chartColors="inIcuColors"
          label="In ICU"
        />
      </div>
    </div>

    <div class="row mt-5" v-if="arrOnVentilators.length > 0">
      <div class="col">
        <h2 class="text-center">On Ventilators</h2>
        <line-chart
          :chartData="arrOnVentilators"
          :options="chartOptions"
          :chartColors="onVentilatorsColors"
          label="On Ventilators"
        />
      </div>
    </div>

    <div class="row mt-5" v-if="arrRecovered.length > 0">
      <div class="col">
        <h2 class="text-center">Recovered</h2>
        <line-chart
          :chartData="arrRecovered"
          :options="chartOptions"
          :chartColors="recoveredColors"
          label="Recovered"
        />
      </div>
    </div>

    <div class="row mt-5 mb-5">
      <div class="col">
        <h2 class="text-center">Deaths</h2>
        <line-chart
          v-if="arrDeaths.length > 0"
          :chartData="arrDeaths"
          :options="chartOptions"
          :chartColors="deathColors"
          label="Deaths"
        />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";

import LineChart from "./components/LineChart";

export default {
  components: {
    LineChart,
  },
  data() {
    return {
      currentNumbers: "",
      arrPositive: [

      ],
      positiveChartColors: {
        borderColor: "#9D8189",
        pointBorderColor: "#9D8189",
        pointBackgroundColor: "#F4ACB7",
        backgroundColor: "#FFCAD4",
      },
      arrHospitalized: [],
      hospitalizedChartColors: {
        borderColor: "#251F47",
        pointBorderColor: "#260F26",
        pointBackgroundColor: "#858EAB",
        backgroundColor: "#858EAB",
      },
      arrInIcu: [],
      inIcuColors: {
        borderColor: "#190B28",
        pointBorderColor: "#190B28",
        pointBackgroundColor: "#E55381",
        backgroundColor: "#E55381",
      },
      arrOnVentilators: [],
      onVentilatorsColors: {
        borderColor: "#784F41",
        pointBorderColor: "#784F41",
        pointBackgroundColor: "#BBE5ED",
        backgroundColor: "#BBE5ED",
      },
      arrRecovered: [
        
      ],
      recoveredColors: {
        borderColor: "#2D6A4F",
        pointBorderColor: "#2D6A4F",
        pointBackgroundColor: "#40916C",
        backgroundColor: "#52B788",
      },

// Some also good yellow colors :)
      // recoveredColors: {
      //   borderColor: "#E85D04",
      //   pointBorderColor: "#E85D04",
      //   pointBackgroundColor: "#F48C06",
      //   backgroundColor: "#FFBA08",
      // },

      arrDeaths: [
        
      ],
      deathColors: {
        borderColor: "#D00000",
        pointBorderColor: "#D00000",
        pointBackgroundColor: "#402A2C",
        backgroundColor: "#03045E",
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
      europeGermany: [
        
      ],
    };
  },

  async created() {
   
    const { data } = await axios.get(
      "https://coviddata.github.io/coviddata/v1/countries/stats.json"
    );
    const country = data.find((country) => country.country.name == "Germany");
    console.log(country);
    const dateItem = Object.keys(country["dates"]);
    console.log(dateItem);

    
    
    let date = [country.dates[1]];
    for (date in country.dates) {
      this.europeGermany.push({ 
        "date": date, 
        "cases": country.dates[date].cumulative.cases, 
        "deaths": country.dates[date].cumulative.deaths, 
        "recoveries": country.dates[date].cumulative.recoveries  
      });
    }
      console.log(this.europeGermany);

     this.europeGermany.forEach(d => {
    const date = moment(d.date, "YYYY-MM-DD").format("DD/MM");
      
      const {
        cases,
        deaths,
        recoveries
      } = d;

     this.arrPositive.push({ "date": date, "total": cases });
     this.arrDeaths.push({ date: date, total: deaths });
     this.arrRecovered.push({ date: date, total: recoveries });
  
     });
    
     console.log(this.arrPositive);
     console.log(this.arrDeaths);
     console.log(this.arrRecovered);

    this.currentNumbers = this.europeGermany.pop();
    this.currentNumbers.date = moment(this.currentNumbers.date, "YYYY-MM-DD").format("DD.MM.YYYY");

  },
};
</script>

<style>
.current-numbers {
  text-align: center;
  font-weight: bold;
  font-size: 150%;

}
</style>
