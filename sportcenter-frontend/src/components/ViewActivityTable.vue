<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
      <br>
    <table bgcolor="black" align="center" width="700">
      <tr bgcolor="pink">
        <th width="100">Name</th>
        <th width="100">Category</th>
        <th width="100">Date</th>
        <th width="100">Capacity</th>
      </tr>
      <tr bgcolor="green">
        <th width="100">Karate</th>
        <th width="100">Fight</th>
        <th width="100">6-March</th>
        <th width="100">12</th>
      </tr>
    </table>
    <br>
    <button type="button">Add Activity</button>
  </div>
</template>

<script>
export default {
  name: 'hello',
  data () {
    return {
      msg: 'View Activities'
    }
  }
  
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

table {
  color: #5a3300;
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

a {
  color: #000000;
}
</style>
