
const searchInput = document.getElementById("plant-search");

const typeFilter = document.getElementById("type-filter");
const familyFilter = document.getElementById("family-filter");
const continentFilter = document.getElementById("continent-filter");

const plantCards = document.querySelectorAll(".plant-card");

const resultsCount = document.getElementById("results-count");
const noResults = document.getElementById("no-results");

function filterPlants() {

    // Get the current search and filter values
    const searchValue = searchInput.value.toLowerCase().trim();

    const selectedType = typeFilter.value;
    const selectedFamily = familyFilter.value;
    const selectedContinent = continentFilter.value;

    let visiblePlants = 0;

    plantCards.forEach(card => {

        // Get information stored in each plant card
        const name = card.dataset.name;
        const scientificName = card.dataset.scientific;
        const type = card.dataset.type;
        const family = card.dataset.family;
        const continent = card.dataset.continent;

        // Check whether each filter matches
        const matchesSearch =
            name.includes(searchValue) ||
            scientificName.includes(searchValue);

        const matchesType =
            selectedType === "" || type === selectedType;

        const matchesFamily =
            selectedFamily === "" || family === selectedFamily;

        const matchesContinent =
            selectedContinent === "" || continent === selectedContinent;

        // Show the card only if every condition matches
        const shouldDisplay =
            matchesSearch &&
            matchesType &&
            matchesFamily &&
            matchesContinent;

        card.style.display = shouldDisplay ? "block" : "none";

        if (shouldDisplay) {
            visiblePlants++;
        }

    });

    // Update the number of visible plants
    resultsCount.textContent =
        `Showing ${visiblePlants} plant${visiblePlants === 1 ? "" : "s"}`;

    // Show a message if no plants match
    noResults.hidden = visiblePlants !== 0;
}

// Listen for changes in the search and filters
searchInput.addEventListener("input", filterPlants);

typeFilter.addEventListener("change", filterPlants);
familyFilter.addEventListener("change", filterPlants);
continentFilter.addEventListener("change", filterPlants);
