<template>
  <div class="popup">
    <div class="popup-content">
      <VBox id="verticalContainer"></VBox>
        <h2>{{ activity.name }}</h2>

        <p>
          Category: {{ activity.category }}<br>
          Date: {{ activity.date }}<br>
          Capacity: {{ activity.capacity }}
        </p>

        <button @click="close">Close</button>
        <button @click="close">Register</button>
      </VBox>
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
    min-width: 260px;
    min-height: 200px;
    width: fit-content;
    height: fit-content;
    /*width: 300px; /* Fixed width */
    /*height: 300px; /* Fixed height */
  }

  #verticalContainer {
    display: flex;
    flex-direction: column;
    justify-content: center;
}
</style>