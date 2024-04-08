<template>
  <div class="popup">
    <div class="popup-content">
      <h2>{{ activity.name }}</h2>
      <p>Category: {{ activity.category }}</p>
      <p>Date: {{ activity.date }}</p>
      <p>Capacity: {{ activity.capacity }}</p>
      <button @click="close">Close</button>
      <button @click="close">Register</button>
    </div>
  </div>
</template>

<script>
export default {
  
  props: ['activity'],
  
  methods: {
    close() {
      this.$emit('close');
    }
  }
};

</script>

<style scoped>

.popup {
  width: 100;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  background-color: #a4e0bb;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  margin-top: 20px; /* Adjust gap between table and popup */
  width: 300px; /* Fixed width */
  height: 300px; /* Fixed height */
}



</style>