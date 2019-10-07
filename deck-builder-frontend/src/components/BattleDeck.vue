<template>
  <div class="battle-deck">
    <div class="battle-deck-header">
      <h4 class="battle-deck-header__title">Battle Deck</h4>
      <font-awesome-icon icon="random"
                         class="random-deck-btn"
                         size="lg"
                         @click="generateRandomDeck" />
    </div>

    <div class="battle-deck-wrapper">
      <div v-for="(card, idx) of data"
           v-bind:key="idx"
           class="cards-wrapper">
        <card :card="card"
              @select-mode="$emit('select-mode', idx)"
              :handle-click-action="clickOnDeckCard" />
      </div>
    </div>
  </div>
</template>

<script>
import Card from "@/components/Card.vue";
export default {
  name: "battle-deck",
  components: { Card },
  props: {
    data: Array
  },
  methods: {
    generateRandomDeck: function() {
      this.$emit("generate-random-deck");
    },
    clickOnDeckCard: function(card) {
      let clickTypes = [];
      if (!card) {
        clickTypes = ["select"];
      } else {
        clickTypes = ["remove", "moreInfo", "upgrade"];
      }
      return clickTypes;
    }
  }
};
</script>

<style scoped>
.battle-deck {
  margin-bottom: 1%;
}

.battle-deck-header {
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
}

.random-deck-btn {
  color: grey;
  margin: auto 0;
}

.random-deck-btn:hover {
  cursor: pointer;
}

.battle-deck-wrapper {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
}

.cards-wrapper {
  flex-basis: 22%;
  margin-top: 2%;
  border: none;
  height: 10rem;
}
</style>
