<template>
  <div class="battle-deck">
    <div class="battle-deck-header">
      <h4 class="battle-deck-header__title">Battle Deck</h4>
      <div class="action-btns">

        <b-button @click="$emit('generate-random-deck')"
                  variant="link"
                  id="random-deck-btn">
          <font-awesome-icon icon="random"
                             size="lg" />
        </b-button>
        <b-tooltip target="random-deck-btn"
                   triggers="hover">
          Generate Random Deck
        </b-tooltip>

        <b-button @click="$emit('share-battle-deck')"
                  variant="link"
                  :disabled="!isFullBattleDeck"
                  id="share-deck-btn">
          <font-awesome-icon icon="share"
                             size="lg" />
        </b-button>
        <b-tooltip target="share-deck-btn"
                   triggers="hover">
          Share Battle Deck
        </b-tooltip>
      </div>
    </div>

    <div class="battle-deck-wrapper">
      <div v-for="(card, idx) of data"
           v-bind:key="idx"
           class="cards-wrapper">
        <card :card="card"
              @remove-mode="$emit('remove-mode', idx)"
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
  computed: {
    isFullBattleDeck: function() {
      return this.data.every(c => c);
    }
  },
  methods: {
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

.action-btns > button {
  color: #e960ec;
  margin: auto 0;
}

.action-btns > button:hover {
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
  height: 9rem;
}

.cards-wrapper >>> .card__elixir {
  width: 30px;
  height: 30px;
  line-height: 28px;
}
</style>
