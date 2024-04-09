<template>
  <div class="ViewActivityTable" id="mainContainer">
    <h1><b>View Activities</b></h1>
    <br>
    
    <input id="inputBox" type="text" v-model="search" placeholder="Search activities">

    <table id="activityTable" align="center" width="700">
      <thead>
        <tr>
          <th width="100">Name</th>
          <th width="100">Category</th>
          <th width="100">Date</th>
          <th width="100">Capacity</th>
        </tr>
      </thead>
      <tbody>
        <template v-if="scheduledActivities.length === 0">
          <tr>
            <td colspan="4">No activities</td>
          </tr>
        </template>

        <template v-else>
          <tr v-for="(activity, index) in filteredActivities" :key="index" @click="showActivityDetails(activity)">
            <td>{{ activity.name }}</td>
            <td>{{ activity.category }}</td>
            <td>{{ activity.date }}</td>
            <td>{{ activity.capacity }}</td>
          </tr>
        </template>

      </tbody>
    </table>
    
    <ViewActivity v-if="selectedActivity" :activity="selectedActivity" @close="closePopup" style="align-self: center;"/>
    
    <br>
    <div class="button-container">
      <button id="optionButton" type="button">Add Activity</button>
    </div>
  </div>
</template>

<script>

import axios from 'axios'
import config from '../../../config'
import ViewActivity from './ViewActivity.vue';

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  data() {
    return {
      
      //scheduledActivities: [],
      filteredActivityData: [],
      selectedActivity: null,
      search:'',



      //logic here would get all activities

      scheduledActivities: [
        { name: 'Borneo', category: 'Expedition', date: '6 march', capacity: 30 },
        { name: 'Trifecta', category: 'YoloSwag', date: '6 april', capacity: 10 },
        { name: 'Running', category: 'Cardio', date: '6 january', capacity: 20 },
        { name: 'INSTAGATION', category: 'Vroom', date: '6 january', capacity: 20 },
      ],
    };
  },


  mounted() {
    // Call method to fetch scheduled activities when the component is mounted
    this.fetchScheduledActivities();
  },



  



  methods: {

    fetchScheduledActivities() {
      // Make HTTP request to fetch scheduled activities from backend
      axios.get('/scheduledActivities')
        .then(response => {
          // Assign response data to scheduledActivities
          this.scheduledActivities = response.data;

          this.scheduledActivitiesTable = response.data.map(activity => ({
          activityName: activity.activity.name,
          activityCategory: activity.activity.category,
          date: activity.date,
          capacity: activity.capacity
        }));
        })
        .catch(error => {
          console.error('Error fetching scheduled activities:', error);
        });
    },

 
    showActivityDetails(activity) {
      this.selectedActivity = activity;
    },
    closePopup() {
      this.selectedActivity = null;
    },


  }, //end of methods

  computed: {
  // Filter activities based on search query
  filteredActivities: function() {
    const query = this.search.toLowerCase();
    return this.scheduledActivities.filter(activity =>
      activity.name.toLowerCase().includes(query) ||
      activity.category.toLowerCase().includes(query) ||
      activity.date.toLowerCase().includes(query) ||
      activity.capacity.toString().includes(query)
    );
  },
  },
  components: {
    ViewActivity
  }
};
</script>

<style>
  #mainDiv {
    font-family: 'Avenir', Helvetica, Arial sans-serif;
    margin: 60px;
    border-radius: 30px;
    padding: 40px;
    box-shadow: 0 50px 50px 0 rgba(209, 184, 52, 0.2);
    color: #2c3e50;
    background: #e4e3e394;
    align-items: center;
    width: 85%;
    max-width: 800px;
    max-height: fit-content;
    text-align: center;
  }

  #containerH {
    display: flex;
    flex-direction: row;
    justify-content: center;
  }

  table {
    background-color: #d3ffd6;
    color: rgb(0, 0, 0);
    font-family: Arial, Helvetica, sans-serif;
    width: 70%;
    border-collapse: collapse;
    margin-top: 20px;
  }


  td, th  {
    border: 1px solid #000000;
    padding: 10px;
  }

  tbody tr:nth-child(even) {
    background-color: #a5a5a5; /* Set even row color */
  }

  tbody tr:nth-child(odd) {
    background-color: #c2c2c2; /* Set odd row color */
  }

  tbody tr:hover {
    background-color: #272727;
    color: rgb(255, 255, 255);
  }

  #inputBox {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
    border-radius: 30px;
    text-align: center;
  }

  #mainButton {
    background-color: #3f5b0b;
    color: white;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 30px;
    width: 100%;
  }

  #optionButton {
    background-color: #3f5b0b;
    color: rgb(255, 255, 255);
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 30px;
    margin-inline: 10px;
  }

  #activityTable {
    align: center;
    background-color: #3f5b0b;
  }
</style>

<style src="../../assets/main.css"></style>