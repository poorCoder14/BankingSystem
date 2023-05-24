<header>
	<!-- Navbar Section  -->
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
		<div class="container-fluid">
			<a class="navbar-brand" href="/BankingSystem/homepage">VirtualbanK</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mr-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" href="#about">My
							profile</a></li>
					<li class="nav-item"><a class="nav-link" href="#discover">Transaction
							Activity</a></li>
					<li class="nav-item"><a class="nav-link" href="#service">Settings
							& Privacy</a></li>
					<li class="nav-item"><a class="nav-link"
						href="change-password.jsp">Change password</a></li>
				</ul>

				<div class="collapse navbar-collapse justify-content-end"
					id="navbarNav">
					<form method="post">
						<ul class="navbar-nav ml-auto mb-2 mb-lg-0">
							<li class="nav-item"><a class="nav-link"
								style="color: white"><i class="fa fa-user-circle-o"
									aria-hidden="true" style="color: white"> </i>Hello,
									${sessionScope.user.customerName}</a></li>
							<li class="nav-item"><button
									class="btn btn-success text-dark" type="submit" name="action"
									value="logOut">
									<i class="fa fa-sign-out" aria-hidden="true"></i> Log Out
								</button></li>
						</ul>

					</form>
				</div>
			</div>
		</div>
	</nav>


</header>