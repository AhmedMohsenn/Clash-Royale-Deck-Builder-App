<template>
  <b-modal id="cards-modal"
           size="lg"
           hide-footer
           title="Select Card"
           @hide="$emit('hide-collections-modal')">
    <p class="my-4">Hello from modal!</p>
  </b-modal>
</template>

<script>
export default {
  name: "cards-modal",
  props: {
    showCollections: Boolean,
    cards: Array,
    cardsRarities: Array
  },
  watch: {
    showCollections: function(value) {
      if (value) {
        this.$bvModal.show("cards-modal");
      }
    }
  }
};
</script>

<style scoped>
</style>
