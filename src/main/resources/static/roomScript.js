const bookingForm = document.getElementById('bookingForm');
const checkInInput = document.getElementById('checkIn');
const checkOutInput = document.getElementById('checkOut');
const totalPriceElement = document.getElementById('totalPrice');
const modal = document.getElementById('confirmationModal');
const finalModal = document.getElementById('finalConfirmationModal');
const confirmBtn = document.getElementById('confirmBtn');
const cancelBtn = document.getElementById('cancelBtn');
const modalName = document.getElementById('modalName');
const modalCheckIn = document.getElementById('modalCheckIn');
const modalCheckOut = document.getElementById('modalCheckOut');
const modalGuests = document.getElementById('modalGuests');
const modalTotal = document.getElementById('modalTotal');

// Set minimum date to today
const today = new Date().toISOString().split('T')[0];
checkInInput.min = today;
checkOutInput.min = today;

// Calculate total price when dates change
function calculateTotal() {
    const checkIn = new Date(checkInInput.value);
    const checkOut = new Date(checkOutInput.value);
    
    if (checkIn && checkOut && checkOut > checkIn) {
        const nights = Math.floor((checkOut - checkIn) / (1000 * 60 * 60 * 24));
        const total = nights * 299; // $299 per night
        totalPriceElement.textContent = `Total: $${total}`;
    } else {
        totalPriceElement.textContent = 'Total: $0';
    }
}

// Update checkout min date when checkin changes
checkInInput.addEventListener('change', function() {
    checkOutInput.min = this.value;
    calculateTotal();
});

checkOutInput.addEventListener('change', calculateTotal);

// Handle form submission
bookingForm.addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Gather user input
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;
    const phone = document.getElementById('phone').value;
    const checkIn = document.getElementById('checkIn').value;
    const checkOut = document.getElementById('checkOut').value;
    const guests = document.getElementById('guests').value;
    const totalPrice = totalPriceElement.textContent.split('$')[1].trim();

    // Populate modal with user details
    modalName.textContent = `Name: ${firstName} ${lastName}`;
    modalCheckIn.textContent = `Check-in: ${checkIn}`;
    modalCheckOut.textContent = `Check-out: ${checkOut}`;
    modalGuests.textContent = `Guests: ${guests}`;
    modalTotal.textContent = `Total: $${totalPrice}`;

    // Show confirmation modal
    modal.style.display = 'flex';
});

// Handle booking confirmation
confirmBtn.addEventListener('click', function() {
    // Hide confirmation modal
    modal.style.display = 'none';

    // Show final confirmation modal
    finalModal.style.display = 'flex';
});

// Handle booking cancellation
cancelBtn.addEventListener('click', function() {
    // Close the modal
    modal.style.display = 'none';
});

// Close the final confirmation modal
function closeFinalModal() {
    finalModal.style.display = 'none';
    bookingForm.reset();
    totalPriceElement.textContent = 'Total: $0';
}

// Handle thumbnail clicks
const thumbnails = document.querySelectorAll('.thumbnail');
const mainImage = document.querySelector('.main-image');

thumbnails.forEach(thumbnail => {
    thumbnail.addEventListener('click', function() {
        mainImage.src = this.src.replace('80/60', '800/600');
    });
});
