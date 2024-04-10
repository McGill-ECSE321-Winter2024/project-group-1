<template>
<div class="ViewActivityTable" id="mainContainer">
  <h1>View Activities</h1>
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
      
      scheduledActivities: [],
      filteredActivityData: [],
      selectedActivity: null,
      search:'',



      // scheduledActivities: [
      //   { name: 'Borneo', category: 'Expedition', date: '6 march', capacity: 30 },
      //   { name: 'Trifecta', category: 'YoloSwag', date: '6 april', capacity: 10 },
      //   { name: 'Running', category: 'Cardio', date: '6 january', capacity: 20 },
      //   { name: 'INSTAGATION', category: 'Vroom', date: '6 january', capacity: 20 },
      // ],
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

<style src="../../assets/main.css"></style>