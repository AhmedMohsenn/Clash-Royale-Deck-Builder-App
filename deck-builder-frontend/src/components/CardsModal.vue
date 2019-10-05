<template>
  <b-modal id="cards-modal"
           hide-footer
           title="Select Card">
    <p class="my-4">Hello from modal!</p>
  </b-modal>
</template>

<script>
export default {
  name: "cards-modal",
  props: {
    cards: {
      type: Array
    },
    cardsRarities: {
      type: Array
    }
  }
};
</script>

<style scoped>
</style>
