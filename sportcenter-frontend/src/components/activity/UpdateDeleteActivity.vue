<template>
  <div style="width: 1200px;">
    <div class="UpdateDeleteActivity" id="mainContainer" style="margin-bottom: 0px;">
      <h1>Activity management</h1>
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
            <tr v-for="(activity, index) in filteredActivities" :key="index">
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
        <button id="mainButton" type="button">Add Activity</button>
      </div>
    </div>

    <div id="horizontalContainer">
      <div id="mainContainer" style="width: 560px;">
        <h1>Update activity</h1>
        <VBox>
          <input id="inputBox" placeholder="Name of activity to update">
          <input id="inputBox" placeholder="Updated category">
          <input id="inputBox" placeholder="Updated capacity">
          <button id="mainButton" type="button">Update</button>
        </VBox>
      </div>

      <div id="mainContainer" style="width: 560px; margin-left: 80px;">
        <h1>Delete activity</h1>
        <input id="inputBox" placeholder="Name of activity to delete">
        <input id="inputBox" placeholder="Confirm name of activity to delete">
        <input id="inputBox" placeholder="Write 'DELETE' to confirm deletion">
        <button id="destroyButton">Delete</button>
      </div>
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

<style src="../../assets/main.css"></style>