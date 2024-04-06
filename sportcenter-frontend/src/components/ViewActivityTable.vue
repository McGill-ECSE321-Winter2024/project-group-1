<template>
  <div class="acitvityTable">
    <h1>View Activities</h1>
    <br>
    <table align="center" width="700">
      <thead>
      <tr>

        <th width="100">Name</th>
        <th width="100">Category</th>
        <th width="100">Date</th>
        <th width="100">Capacity</th>

      </tr>
      </thead>

      <!-- Table body-->

      <tbody id="activityData"></tbody>

      <tbody>
        <tr v-for="(activity, index) in activityData" :key="index" @click="showActivityDetails(activity)">
          <td>{{ activity.name }}</td>
          <td>{{ activity.category }}</td>
          <td>{{ activity.date }}</td>
          <td>{{ activity.capacity }}</td>
        </tr>
        <ViewActivity v-if="selectedActivity" :activity="selectedActivity" @close="closePopup" />
      </tbody>


    </table>
    <br>

  </div>
</template>

<script>

import ViewActivity from './ViewActivity.vue';

export default {
  data() {
    return {
      
      //logic here would get all activities

      activityData: [
        { name: 'Borneo', category: 'Expedition', date: '6 march', capacity: 30 },
        { name: 'Trifecta', category: 'YoloSwag', date: '6 april', capacity: 10 },
        { name: 'Running', category: 'Cardio', date: '6 january', capacity: 20 },
        { name: 'INSTAGATION', category: 'Vroom', date: '6 january', capacity: 20 },
      ],
      selectedActivity: null
    };
  },
  methods: {
    showActivityDetails(activity) {
      this.selectedActivity = activity;
    },
    closePopup() {
      this.selectedActivity = null;
    }
  },
  components: {
    ViewActivity
  }
  };


  //inject the content after the table load!
  function loadActivityData(activityData) {

    const activityBody = document.getElementById('activityData');
    let dataHtml = '';

    for(let activity of activityData) {
      dataHtml += `<tr><td>${activity.name}</td><td>${activity.category}</td><td>${activity.date}</td><td>${activity.capacity}</td></tr>`;
    }

    activityBody.innerHTML = dataHtml;

    console.log(dataHtml)

  }

  //};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

table {
  background-color: #d0a4dd;
  color: aquamarine;
  font-family: Arial, Helvetica, sans-serif;
  width: 70%;
  border-collapse: collapse;
}


td, th  {
  border: 1px solid #d0a4dd;
  padding: 10px;
}

tbody tr:nth-child(even) {
  background-color: #8b0808; /* Set even row color */
}

tbody tr:nth-child(odd) {
  background-color: #385209; /* Set odd row color */
}

tbody tr:hover {
  background-color: #e0cfb9;
  color: rgb(0, 0, 0);
}



h1, h2 {
  font-weight: bold;
  color: #740404;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}


</style>
