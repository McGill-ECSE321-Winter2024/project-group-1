<template>
  <div id="Main">
    <H1><b> Today's Schedule</b></H1>
    <Table id="todaysActivities" align="center" width="700">
      <thead>
        <tr>
          <th width="100">Name</th>
          <th width="100">Category</th>
          <th width="100">Time</th>
          <th width="100">Capacity</th>
        </tr>
      </thead>
      <tbody>
        <template v-if="scheduledACtivities.lenght === 0">
          <tr>
            <td colspan="4">No activities scheduled for today</td>
          </tr>
        </template>
        <template v-else>
          <tr
            v-for="(activity, index) in filteredActivities : in scheduledActivities"
            :key="index"
            @click="showActivityDetails(activity)"
          >
            <td>{{ activity.name }}</td>
            <td>{{ activity.category }}</td>
            <td>{{ activity.time }}</td>
            <td>{{ activity.capacity }}</td>
          </tr>
        </template>
        <ViewActivity
          v-if="scheduledACtivity"
          :activity="scheduledActivity"
          @close="closePopup"
        />
      </tbody>
    </Table>
  </div>
</template>

<script>

import axios from 'axios';
import config from '../config';
import ViewActivity from './ViewActivity.vue';

const frontendUrl =  'http://' + config.frontendHost + ':' + config.frontendPort;
const backendUrl =  'http://' + config.backendHost + ':' + config.backendPort;
const AXIOS= axios.create({
    baseURL: backendUrl,
    headers: {
        'Access-Control-Allow-Origin': frontendUrl
    }
});

export default {
    mounted() {
        this.fetchScheduledActivities();
    }

    methods: {
        fetchScheduledActivities() {
            axios.get('/scheduledActivities').then(response => {
                this.schedulesActivities= response.data
                this.scheduledActivities = response.data.map(activity => ({
                activityName: activity.activity.name,
                activityCategory: activity.activity.category,
                time: activity.time,
                capacity: activity.capacity
                date: activity.date
                }));
            }).catch(e => {
                console.error('error fetching scheduled activities');
            });
        }

        showActivityDetails(activity) {
            this.scheduledActivity = activity;
        }

        closePopup() {
            this.scheduledActivity = null;
        }
    }

    computed: {
        filteredActivities: function() {
            const today = new Date();
            return this.scheduledActivities.filter(activity => activity.date.includes(today));
        }
    }

    components: {
        ViewActivity
    }

}
</script>

<style>
::placeholder {
  text-align: center;
}

#Main {
  font-family: "Avenir", Helvetica, Arial sans-serif;
  margin: 60px;
  border-radius: 30px;
  padding: 40px;
  box-shadow: 0 50px 50px 0 rgba(209, 184, 52, 0.2);
  color: #2c3e50;
  background: #e4e3e394;
  width: 800px;
  max-width: fit-content;
  max-height: fit-content;
  text-align: center;
}
</style>
