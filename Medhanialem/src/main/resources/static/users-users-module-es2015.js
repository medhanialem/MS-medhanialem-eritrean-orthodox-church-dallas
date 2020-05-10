(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["users-users-module"],{

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/users/user-edit/user-edit.component.html":
/*!************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/users/user-edit/user-edit.component.html ***!
  \************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div class=\"mainDiv\">\n    <div class=\"row\">\n      <div class=\"col-12\">\n        <mat-card class='loginCard'>\n          <mat-card-header class='loginHeader'>\n            <mat-toolbar class=\"matToolBar\">\n              <span>Edit User</span>\n              <span class=\"fill-remaining-space\"></span>\n              <button class=\"btn-dialog-close\" mat-stroked-button (click)=\"onClose()\" tabIndex=\"-1\">\n                <mat-icon>clear</mat-icon>\n              </button>\n            </mat-toolbar>\n          </mat-card-header>\n          <mat-card-content>\n            <form [formGroup]=\"editUserForm\" class=\"registrationForm\">\n              <div class='row'>\n                <div class=\"col-12\">\n                  <mat-form-field>\n                    <input matInput formControlName='user' placeholder=\"User\" />\n                  </mat-form-field>\n                </div>\n                <div class=\"col-12\">\n                  <mat-form-field class='formField'>\n                    <input matInput formControlName='email' placeholder=\"Email\" />\n                  </mat-form-field>\n                </div>\n                <div class=\"col-12\">\n                  <mat-form-field class='formField'>\n                    <input matInput formControlName='phoneNo' placeholder=\"Phone number\" />\n                  </mat-form-field>\n                </div>\n                <div class=\"col-12\">\n                  <mat-form-field>\n                    <mat-label>Select Role(s)</mat-label>\n                    <mat-select formControlName='roles' multiple #ROLE>\n                      <mat-option [value]='roles.sundaySchool.toString()'>{{roles.sundaySchool}}</mat-option>\n                      <mat-option [value]='roles.secretary.toString()'>{{roles.secretary}}</mat-option>\n                      <mat-option [value]='roles.sebeka_gubae.toString()'>{{roles.sebeka_gubae}}</mat-option>\n                      <mat-option [value]='roles.abo_wenber_sebeka_gubae.toString()'>{{roles.abo_wenber_sebeka_gubae}}</mat-option>\n                    </mat-select>\n                    <mat-error>You must select at least one role.</mat-error>\n                  </mat-form-field>\n                </div>\n\n                <div class=\"col-12\">\n                  <mat-form-field class='formField'>\n                    <mat-label>Active/Inactive</mat-label>\n                    <mat-select formControlName='status' [(ngModel)]=\"selected\">\n                    <mat-option *ngFor = \"let activeStatus of activeInactive\" [value] = \"activeStatus\">{{ activeStatus }}</mat-option>\n                    </mat-select>\n                  </mat-form-field>\n                </div>\n\n                <!-- <div class=\"col-12\">\n                  <mat-form-field class='formField'>\n                    <mat-radio-group formControlName='status' aria-label=\"Select an option\">\n                      <mat-radio-button [checked]='true'>Active</mat-radio-button>\n                      <mat-radio-button [checked]='false'>Inactive</mat-radio-button>\n                    </mat-radio-group>\n                  </mat-form-field>\n                </div> -->\n\n                <!-- <div class=\"col-12\">\n                  <mat-form-field class='formField'>\n                    <input matInput formControlName='password' placeholder=\"Password\" type='password' />\n                    <mat-error>Password field is required</mat-error>\n                  </mat-form-field>\n                </div> -->  \n                <!-- <div class=\"col-12\">\n                  <mat-form-field class='formField'>\n                    <input matInput formControlName='confirmPassword' placeholder=\"Confirm-Password\" type='password' />\n                    <mat-error>Confirm-Password field is required</mat-error>\n                    <div *ngIf=\"showConfirmPasswordErrorMessage()\"\n                      class=\"ng-tns-c12-17 ng-trigger ng-trigger-transitionMessages ng-star-inserted\"\n                      style=\"opacity: 1; transform: translateY(0%);\">\n                      <label class=\"mat-error lblConfirmError\" role=\"alert\" id=\"mat-error-3\">Password did not match</label>\n                    </div>\n                  </mat-form-field>\n                </div> -->\n                <div class='col-12 row'>\n                  <div class=\"col-2\">\n  \n                  </div>\n                  <div class=\"col-8 divButtons\">\n                    <button mat-raised-button color='primary' class='btnLogin' (click)='onSave()'\n                      [disabled]=\"saveUserDisabled()\">Save</button>\n                    <!-- <button mat-raised-button color='warn' class=\"btnClear\" (click)='onClear()'>Clear</button> -->\n                  </div>\n                  <div class=\"col-2\">\n  \n                  </div>\n  \n                </div>\n              </div>\n            </form>\n          </mat-card-content>\n        </mat-card>\n      </div>\n    </div>\n  </div>");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/users/user-registration/user-registration.component.html":
/*!****************************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/users/user-registration/user-registration.component.html ***!
  \****************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div class=\"mainDiv\">\n  <div class=\"row\">\n    <div class=\"col-12\">\n      <mat-card class='loginCard'>\n        <mat-card-header class='loginHeader'>\n          <mat-toolbar class=\"matToolBar\">\n            <span>Add New User</span>\n            <span class=\"fill-remaining-space\"></span>\n            <button class=\"btn-dialog-close\" mat-stroked-button (click)=\"onClose()\" tabIndex=\"-1\">\n              <mat-icon>clear</mat-icon>\n            </button>\n          </mat-toolbar>\n        </mat-card-header>\n        <mat-card-content>\n          <form [formGroup]=\"registrationForm\" class=\"registrationForm\">\n            <div class='row'>\n              <div class=\"col-12\">\n                <mat-form-field>\n                  <mat-label>Select user</mat-label>\n                  <mat-select formControlName='user' (selectionChange)=\"onMemberSelected($event)\" #USER>\n                    <mat-option *ngFor=\"let member of members\" [value]='member'>\n                      {{member.firstName +' ' + member.middleName +' ' + member.lastName }}\n                    </mat-option>\n                  </mat-select>\n                  <mat-error>Please select a user.</mat-error>\n                </mat-form-field>\n              </div>\n              <div class=\"col-12\">\n                <mat-form-field class='formField'>\n                  <input matInput formControlName='email' placeholder=\"Email\" [value]='email' />\n                </mat-form-field>\n              </div>\n              <div class=\"col-12\">\n                <mat-form-field class='formField'>\n                  <input matInput formControlName='phoneNo' placeholder=\"Phone number\" [value]='phoneNo'\n                    disabled='true' />\n                </mat-form-field>\n              </div>\n              <div class=\"col-12\">\n                <mat-form-field>\n                  <mat-label>Select Role(s)</mat-label>\n                  <mat-select formControlName='roles' multiple #ROLE>\n                    <mat-option [value]='roles.sundaySchool.toString()'>{{roles.sundaySchool}}</mat-option>\n                    <mat-option [value]='roles.secretary.toString()'>{{roles.secretary}}</mat-option>\n                    <mat-option [value]='roles.sebeka_gubae.toString()'>{{roles.sebeka_gubae}}</mat-option>\n                    <mat-option [value]='roles.abo_wenber_sebeka_gubae.toString()'>{{roles.abo_wenber_sebeka_gubae}}</mat-option>\n                  </mat-select>\n                  <mat-error>You must select at least one role.</mat-error>\n                </mat-form-field>\n              </div>\n              <div class=\"col-12\">\n                <mat-form-field class='formField'>\n                  <input matInput formControlName='password' placeholder=\"Password\" type='password' />\n                  <mat-error>Password field is required</mat-error>\n                </mat-form-field>\n              </div>\n              <div class=\"col-12\">\n                <mat-form-field class='formField'>\n                  <input matInput formControlName='confirmPassword' placeholder=\"Confirm-Password\" type='password' />\n                  <mat-error>Confirm-Password field is required</mat-error>\n                  <div *ngIf=\"showConfirmPasswordErrorMessage()\"\n                    class=\"ng-tns-c12-17 ng-trigger ng-trigger-transitionMessages ng-star-inserted\"\n                    style=\"opacity: 1; transform: translateY(0%);\">\n                    <label class=\"mat-error lblConfirmError\" role=\"alert\" id=\"mat-error-3\">Password did not match</label>\n                  </div>\n                </mat-form-field>\n              </div>\n              <div class='col-12 row'>\n                <div class=\"col-2\">\n\n                </div>\n                <div class=\"col-8 divButtons\">\n                  <button mat-raised-button color='primary' class='btnLogin' (click)='onSave()'\n                    [disabled]=\"saveUserDisabled()\">Save</button>\n                  <button mat-raised-button color='warn' class=\"btnClear\" (click)='onClear()'>Clear</button>\n                </div>\n                <div class=\"col-2\">\n\n                </div>\n\n              </div>\n            </div>\n          </form>\n        </mat-card-content>\n      </mat-card>\n    </div>\n  </div>\n</div>");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/users/users.component.html":
/*!**********************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/users/users.component.html ***!
  \**********************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div class=\"usersContainer\">\n  <hr id=\"hr_user_header1\"/>\n  <div class=\"search-div\">\n    <button mat-raised-button (click)=\"onAddUser()\" color=\"primary\">\n      <mat-icon>person_add</mat-icon>{{\" \"}}User\n    </button>\n    <mat-form-field class=\"search-form-field\" floatLabel=\"never\">\n      <input matInput [(ngModel)]=\"searchKey\" placeholder=\"Search\" autocomplete=\"off\" (keyup)=\"applyFilter()\">\n      <button mat-button matSuffix mat-icon-button aria-label=\"Clear\" *ngIf=\"searchKey\" (click)=\"onSearchClear()\">\n        <mat-icon>close</mat-icon>\n      </button>\n    </mat-form-field>\n    <mat-checkbox class = \"showAllUsers\" color=\"primary\" [(ngModel)]=\"showAll\" (change)=\"showAllUsers()\">Show All</mat-checkbox>\n  </div>\n  <hr id=\"hr_user_header2\"/>\n  <div class=\"mat-elevation-z8\">\n    <mat-table [dataSource]=\"userListData\" matSort class='matTable' >\n      <ng-container matColumnDef=\"userId\">\n        <mat-header-cell class=\"user_id_header\" id =\"user_id\" *matHeaderCellDef mat-sort-header>User Id</mat-header-cell>\n        <mat-cell id =\"user_id\" *matCellDef=\"let element\">{{ element.userId }}</mat-cell>\n      </ng-container>\n      <ng-container matColumnDef=\"fullName\">\n        <mat-header-cell id = \"user_fullName\" *matHeaderCellDef>Full name</mat-header-cell>\n        <mat-cell id = \"user_fullName\" *matCellDef=\"let element\">{{ element.firstName + ' ' +element.middleName+' '+ element.lastName }}</mat-cell>\n      </ng-container>    \n      <ng-container matColumnDef=\"email\">\n        <mat-header-cell id=\"user_email\" *matHeaderCellDef mat-sort-header>Email</mat-header-cell>\n        <mat-cell id=\"user_email\" *matCellDef=\"let element\">{{ element.email }}</mat-cell>\n      </ng-container>\n      <ng-container matColumnDef=\"phoneNo\">\n        <mat-header-cell id = \"user_mobile\" *matHeaderCellDef>Phone No.</mat-header-cell>\n        <mat-cell id = \"user_mobile\" *matCellDef=\"let element\">{{ element.phoneNo }}</mat-cell>\n      </ng-container>\n      <ng-container matColumnDef=\"createdDate\">\n        <mat-header-cell id=\"created_date\" *matHeaderCellDef mat-sort-header>Created On</mat-header-cell>\n        <mat-cell id=\"created_date\" *matCellDef=\"let element\">{{ toShortDate(element.createdDate) }}</mat-cell>\n      </ng-container>\n      <ng-container matColumnDef=\"updatedDate\">\n        <mat-header-cell id=\"updated_date\" *matHeaderCellDef mat-sort-header>Updated On</mat-header-cell>\n        <mat-cell id=\"updated_date\" *matCellDef=\"let element\">{{ toShortDate(element.updatedDate) }}</mat-cell>\n      </ng-container>\n      <ng-container matColumnDef=\"roles\">\n        <mat-header-cell id=\"user_roles\" *matHeaderCellDef mat-sort-header>Roles</mat-header-cell>\n        <mat-cell id=\"user_roles\" *matCellDef=\"let element\">{{ getRoles(element) }}</mat-cell>\n      </ng-container>\n      <ng-container matColumnDef=\"isActive\">\n        <mat-header-cell id=\"is_active\" *matHeaderCellDef mat-sort-header>Active</mat-header-cell>\n        <mat-cell id=\"is_active\" *matCellDef=\"let element\">{{ element.isActive }}</mat-cell>\n      </ng-container>\n      <ng-container matColumnDef=\"actions\">\n        <mat-header-cell *matHeaderCellDef id = \"user_actions\">Actions</mat-header-cell>\n        <mat-cell *matCellDef=\"let row\" id = \"user_actions\">\n          <button mat-icon-button (click)=\"onEdit(row)\">\n            <mat-icon>launch</mat-icon>\n          </button>\n          <!-- <button mat-icon-button color=\"warn\" (click)=\"onDeactivate(row.$key)\">\n            <mat-icon>delete_outline</mat-icon>\n          </button> -->\n        </mat-cell>\n      </ng-container>\n      <ng-container matColumnDef=\"loading\">\n        <mat-footer-cell *matFooterCellDef colspan=\"6\">\n          <!-- Loading data... -->\n          <img src=\"../../../assets/fading_squares.gif\" alt=\"Loading image\" height=\"40\" width=\"150\">\n        </mat-footer-cell>\n      </ng-container>\n      <ng-container matColumnDef=\"noData\">\n        <mat-footer-cell *matFooterCellDef colspan=\"6\">\n          No data.\n        </mat-footer-cell>\n      </ng-container>\n      <mat-footer-row *matFooterRowDef=\"['loading']\" [ngClass]=\"{'hide':!isLoading}\"></mat-footer-row>\n      <mat-header-row *matHeaderRowDef=\"displayedColumns\"></mat-header-row>\n      <mat-row *matRowDef=\"let row; columns: displayedColumns;\"></mat-row>\n      \n    </mat-table>\n    <mat-paginator [pageSizeOptions]=\"[5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 75, 100]\" [pageSize]=\"10\"\n      showFirstLastButtons></mat-paginator>\n  </div>\n</div>\n");

/***/ }),

/***/ "./src/app/users/user-edit/user-edit.component.css":
/*!*********************************************************!*\
  !*** ./src/app/users/user-edit/user-edit.component.css ***!
  \*********************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".mainDiv {\n    margin-top: -20px;\n    margin-bottom: -20px;\n}\n\n.matToolBar {\n    background-color: #3f51b5;\n    color: white;\n    font-size: 25px;\n}\n\n.fill-remaining-space {\n    -webkit-box-flex: 1;\n            flex: 1 1 auto;\n}\n\n.loginCard {\n    width: 100%;\n    margin-top: 5%;\n}\n\n.loginHeader {\n    display: -webkit-box;\n    display: flex;\n    -webkit-box-orient: horizontal;\n    -webkit-box-direction: normal;\n            flex-direction: row;\n    -webkit-box-align: center;\n            align-items: center;\n    flex-flow: column;\n}\n\n.registrationForm{\n    margin-top: 40px !important;\n  }\n\n.btnLogin {\nwidth:50%;\n}\n\n.btnClear {\n    margin-left: 20px !important;\n    width:40%;\n}\n\n.divButtons{\n    text-align: center;\n    margin-top: 40px;\n}\n\n.divFormFields {\n    margin-top:5%;\n}\n\nmat-form-field {\n    width: 100%;\n    margin-left: 5px;\n    margin-right: 5px;\n}\n\n.custom-error{\n    display: block;\n    color: #f44336;\n}\n\n.lblConfirmError {\n    font-size: 10px;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInVzZXJzL3VzZXItZWRpdC91c2VyLWVkaXQuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtJQUNJLGlCQUFpQjtJQUNqQixvQkFBb0I7QUFDeEI7O0FBRUE7SUFDSSx5QkFBeUI7SUFDekIsWUFBWTtJQUNaLGVBQWU7QUFDbkI7O0FBRUE7SUFDSSxtQkFBYztZQUFkLGNBQWM7QUFDbEI7O0FBQ0E7SUFDSSxXQUFXO0lBQ1gsY0FBYztBQUNsQjs7QUFFQTtJQUNJLG9CQUFhO0lBQWIsYUFBYTtJQUNiLDhCQUFtQjtJQUFuQiw2QkFBbUI7WUFBbkIsbUJBQW1CO0lBQ25CLHlCQUFtQjtZQUFuQixtQkFBbUI7SUFDbkIsaUJBQWlCO0FBQ3JCOztBQUdFO0lBQ0UsMkJBQTJCO0VBQzdCOztBQUdGO0FBQ0EsU0FBUztBQUNUOztBQUVBO0lBQ0ksNEJBQTRCO0lBQzVCLFNBQVM7QUFDYjs7QUFFQTtJQUNJLGtCQUFrQjtJQUNsQixnQkFBZ0I7QUFDcEI7O0FBRUE7SUFDSSxhQUFhO0FBQ2pCOztBQUVBO0lBQ0ksV0FBVztJQUNYLGdCQUFnQjtJQUNoQixpQkFBaUI7QUFDckI7O0FBRUE7SUFDSSxjQUFjO0lBQ2QsY0FBYztBQUNsQjs7QUFFQTtJQUNJLGVBQWU7QUFDbkIiLCJmaWxlIjoidXNlcnMvdXNlci1lZGl0L3VzZXItZWRpdC5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLm1haW5EaXYge1xuICAgIG1hcmdpbi10b3A6IC0yMHB4O1xuICAgIG1hcmdpbi1ib3R0b206IC0yMHB4O1xufVxuXG4ubWF0VG9vbEJhciB7XG4gICAgYmFja2dyb3VuZC1jb2xvcjogIzNmNTFiNTtcbiAgICBjb2xvcjogd2hpdGU7XG4gICAgZm9udC1zaXplOiAyNXB4O1xufVxuXG4uZmlsbC1yZW1haW5pbmctc3BhY2Uge1xuICAgIGZsZXg6IDEgMSBhdXRvO1xufVxuLmxvZ2luQ2FyZCB7XG4gICAgd2lkdGg6IDEwMCU7XG4gICAgbWFyZ2luLXRvcDogNSU7XG59XG5cbi5sb2dpbkhlYWRlciB7XG4gICAgZGlzcGxheTogZmxleDtcbiAgICBmbGV4LWRpcmVjdGlvbjogcm93O1xuICAgIGFsaWduLWl0ZW1zOiBjZW50ZXI7XG4gICAgZmxleC1mbG93OiBjb2x1bW47XG59XG5cblxuICAucmVnaXN0cmF0aW9uRm9ybXtcbiAgICBtYXJnaW4tdG9wOiA0MHB4ICFpbXBvcnRhbnQ7XG4gIH1cblxuXG4uYnRuTG9naW4ge1xud2lkdGg6NTAlO1xufVxuXG4uYnRuQ2xlYXIge1xuICAgIG1hcmdpbi1sZWZ0OiAyMHB4ICFpbXBvcnRhbnQ7XG4gICAgd2lkdGg6NDAlO1xufVxuXG4uZGl2QnV0dG9uc3tcbiAgICB0ZXh0LWFsaWduOiBjZW50ZXI7XG4gICAgbWFyZ2luLXRvcDogNDBweDtcbn1cblxuLmRpdkZvcm1GaWVsZHMge1xuICAgIG1hcmdpbi10b3A6NSU7XG59XG5cbm1hdC1mb3JtLWZpZWxkIHtcbiAgICB3aWR0aDogMTAwJTtcbiAgICBtYXJnaW4tbGVmdDogNXB4O1xuICAgIG1hcmdpbi1yaWdodDogNXB4O1xufVxuXG4uY3VzdG9tLWVycm9ye1xuICAgIGRpc3BsYXk6IGJsb2NrO1xuICAgIGNvbG9yOiAjZjQ0MzM2O1xufVxuXG4ubGJsQ29uZmlybUVycm9yIHtcbiAgICBmb250LXNpemU6IDEwcHg7XG59XG4iXX0= */");

/***/ }),

/***/ "./src/app/users/user-edit/user-edit.component.ts":
/*!********************************************************!*\
  !*** ./src/app/users/user-edit/user-edit.component.ts ***!
  \********************************************************/
/*! exports provided: UserEditComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserEditComponent", function() { return UserEditComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var src_app_shared_roles__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/shared/roles */ "./src/app/shared/roles.ts");
/* harmony import */ var src_app_components_members_shared_member_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/components/members/shared/member.service */ "./src/app/components/members/shared/member.service.ts");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm2015/material.js");
/* harmony import */ var src_app_components_members_add_member_dialog_close_dialog_close_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! src/app/components/members/add-member-dialog-close/dialog-close.component */ "./src/app/components/members/add-member-dialog-close/dialog-close.component.ts");
/* harmony import */ var _user_model__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../user.model */ "./src/app/users/user.model.ts");
/* harmony import */ var _user_service__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ../user.service */ "./src/app/users/user.service.ts");









let UserEditComponent = class UserEditComponent {
    constructor(data, memberService, fb, dialog, dialogRef, userService) {
        this.data = data;
        this.memberService = memberService;
        this.fb = fb;
        this.dialog = dialog;
        this.dialogRef = dialogRef;
        this.userService = userService;
        // members: Member[] = [];
        this.roles = src_app_shared_roles__WEBPACK_IMPORTED_MODULE_3__["Roles"];
        this.user = new _user_model__WEBPACK_IMPORTED_MODULE_7__["UserModel"]();
        this.activeInactive = ['Active', 'Inactive'];
        this.selected = '';
        this.editUserForm = fb.group({
            user: new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormControl"]({ value: data.firstName + ' ' + data.middleName + ' ' + data.lastName, disabled: true }, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required),
            email: new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormControl"]({ value: data.email, disabled: true }),
            phoneNo: new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormControl"]({ value: data.phoneNo, disabled: true }),
            // password: [null, [Validators.required, Validators.minLength(5)]],
            // confirmPassword: [null],
            roles: [data.roles, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            status: [data.isActive]
        });
    }
    ngOnInit() {
        // this.getAllUserNames();
        this.data.isActive ? this.selected = 'Active' : this.selected = 'Inactive';
    }
    // getAllUserNames() {
    //   this.memberService.getAllUserNames().subscribe(
    //     (response) => {
    //       this.members = response as Member[];
    //     },
    //     (error) => {
    //       console.log(error);
    //     },
    //     () => {
    //       // To-Do
    //     }
    //   );
    // }
    // onMemberSelected(event: any) {
    //   this.email = event.value.email;
    //   this.phoneNo = event.value.homePhoneNo;
    // }
    onSave() {
        if (this.editUserForm.valid && this.comparePasswords()) {
            this.populateUser();
            this.userService.addUser(this.user).subscribe((result) => {
                console.log(result);
                this.dialogRef.close(null);
            }, (error) => {
                console.log(error);
            }, () => { });
        }
    }
    populateUser() {
        this.user.firstName = this.data.firstName;
        this.user.middleName = this.data.middleName;
        this.user.lastName = this.data.lastName;
        this.user.email = this.data.email;
        this.user.isActive = true;
        this.user.createdDate = this.data.createdDate;
        this.user.updatedDate = new Date();
        this.user.phoneNo = this.data.phoneNo;
        this.user.roles = this.editUserForm.value.roles.toString().split(',');
        this.user.userId = this.data.userId;
    }
    // onClear() {
    //   this.registrationForm.reset();
    // }
    onClose() {
        if (this.editUserForm.dirty) {
            const dialogConfig = new _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialogConfig"]();
            dialogConfig.disableClose = true;
            dialogConfig.autoFocus = true;
            dialogConfig.width = '30%';
            const dialogRef = this.dialog.open(src_app_components_members_add_member_dialog_close_dialog_close_component__WEBPACK_IMPORTED_MODULE_6__["DialogCloseComponent"], dialogConfig);
            dialogRef.afterClosed().subscribe(result => {
                if (result === 'yes') {
                    this.dialogRef.close(null);
                }
            });
        }
        else {
            this.dialogRef.close(null);
        }
    }
    saveUserDisabled() {
        //return !this.editUserForm.valid || !this.comparePasswords() || !this.editUserForm.dirty;
        return !this.editUserForm.valid || !this.editUserForm.dirty;
    }
    comparePasswords() {
        if (this.editUserForm.value.confirmPassword != null) {
            return this.editUserForm.value.password === this.editUserForm.value.confirmPassword;
        }
        return false;
    }
    showConfirmPasswordErrorMessage() {
        if (this.editUserForm != null &&
            this.editUserForm.dirty &&
            this.editUserForm.value.confirmPassword != null &&
            this.editUserForm.value.confirmPassword.toString().length > 0) {
            const password = this.editUserForm.value.password;
            const confirmPassword = this.editUserForm.value.confirmPassword;
            return password === confirmPassword ? false : true;
        }
        return false;
    }
};
UserEditComponent.ctorParameters = () => [
    { type: _user_model__WEBPACK_IMPORTED_MODULE_7__["UserModel"], decorators: [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"], args: [_angular_material__WEBPACK_IMPORTED_MODULE_5__["MAT_DIALOG_DATA"],] }] },
    { type: src_app_components_members_shared_member_service__WEBPACK_IMPORTED_MODULE_4__["MemberService"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] },
    { type: _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialog"] },
    { type: _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialogRef"] },
    { type: _user_service__WEBPACK_IMPORTED_MODULE_8__["UserService"] }
];
UserEditComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-user-edit',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./user-edit.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/users/user-edit/user-edit.component.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ./user-edit.component.css */ "./src/app/users/user-edit/user-edit.component.css")).default]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](0, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material__WEBPACK_IMPORTED_MODULE_5__["MAT_DIALOG_DATA"])),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_user_model__WEBPACK_IMPORTED_MODULE_7__["UserModel"],
        src_app_components_members_shared_member_service__WEBPACK_IMPORTED_MODULE_4__["MemberService"],
        _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"],
        _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialog"],
        _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialogRef"],
        _user_service__WEBPACK_IMPORTED_MODULE_8__["UserService"]])
], UserEditComponent);



/***/ }),

/***/ "./src/app/users/user-registration/user-registration.component.css":
/*!*************************************************************************!*\
  !*** ./src/app/users/user-registration/user-registration.component.css ***!
  \*************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".mainDiv {\n    margin-top: -20px;\n    margin-bottom: -20px;\n}\n\n.matToolBar {\n    background-color: #3f51b5;\n    color: white;\n    font-size: 25px;\n}\n\n.fill-remaining-space {\n    -webkit-box-flex: 1;\n            flex: 1 1 auto;\n}\n\n.loginCard {\n    width: 100%;\n    margin-top: 5%;\n}\n\n.loginHeader {\n    display: -webkit-box;\n    display: flex;\n    -webkit-box-orient: horizontal;\n    -webkit-box-direction: normal;\n            flex-direction: row;\n    -webkit-box-align: center;\n            align-items: center;\n    flex-flow: column;\n}\n\n.registrationForm{\n    margin-top: 40px !important;\n  }\n\n.btnLogin {\nwidth:50%;\n}\n\n.btnClear {\n    margin-left: 20px !important;\n    width:40%;\n}\n\n.divButtons{\n    text-align: center;\n    margin-top: 40px;\n}\n\n.divFormFields {\n    margin-top:5%;\n}\n\nmat-form-field {\n    width: 100%;\n    margin-left: 5px;\n    margin-right: 5px;\n}\n\n.custom-error{\n    display: block;\n    color: #f44336;\n}\n\n.lblConfirmError {\n    font-size: 10px;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInVzZXJzL3VzZXItcmVnaXN0cmF0aW9uL3VzZXItcmVnaXN0cmF0aW9uLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7SUFDSSxpQkFBaUI7SUFDakIsb0JBQW9CO0FBQ3hCOztBQUVBO0lBQ0kseUJBQXlCO0lBQ3pCLFlBQVk7SUFDWixlQUFlO0FBQ25COztBQUVBO0lBQ0ksbUJBQWM7WUFBZCxjQUFjO0FBQ2xCOztBQUNBO0lBQ0ksV0FBVztJQUNYLGNBQWM7QUFDbEI7O0FBRUE7SUFDSSxvQkFBYTtJQUFiLGFBQWE7SUFDYiw4QkFBbUI7SUFBbkIsNkJBQW1CO1lBQW5CLG1CQUFtQjtJQUNuQix5QkFBbUI7WUFBbkIsbUJBQW1CO0lBQ25CLGlCQUFpQjtBQUNyQjs7QUFHRTtJQUNFLDJCQUEyQjtFQUM3Qjs7QUFHRjtBQUNBLFNBQVM7QUFDVDs7QUFFQTtJQUNJLDRCQUE0QjtJQUM1QixTQUFTO0FBQ2I7O0FBRUE7SUFDSSxrQkFBa0I7SUFDbEIsZ0JBQWdCO0FBQ3BCOztBQUVBO0lBQ0ksYUFBYTtBQUNqQjs7QUFFQTtJQUNJLFdBQVc7SUFDWCxnQkFBZ0I7SUFDaEIsaUJBQWlCO0FBQ3JCOztBQUVBO0lBQ0ksY0FBYztJQUNkLGNBQWM7QUFDbEI7O0FBRUE7SUFDSSxlQUFlO0FBQ25CIiwiZmlsZSI6InVzZXJzL3VzZXItcmVnaXN0cmF0aW9uL3VzZXItcmVnaXN0cmF0aW9uLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIubWFpbkRpdiB7XG4gICAgbWFyZ2luLXRvcDogLTIwcHg7XG4gICAgbWFyZ2luLWJvdHRvbTogLTIwcHg7XG59XG5cbi5tYXRUb29sQmFyIHtcbiAgICBiYWNrZ3JvdW5kLWNvbG9yOiAjM2Y1MWI1O1xuICAgIGNvbG9yOiB3aGl0ZTtcbiAgICBmb250LXNpemU6IDI1cHg7XG59XG5cbi5maWxsLXJlbWFpbmluZy1zcGFjZSB7XG4gICAgZmxleDogMSAxIGF1dG87XG59XG4ubG9naW5DYXJkIHtcbiAgICB3aWR0aDogMTAwJTtcbiAgICBtYXJnaW4tdG9wOiA1JTtcbn1cblxuLmxvZ2luSGVhZGVyIHtcbiAgICBkaXNwbGF5OiBmbGV4O1xuICAgIGZsZXgtZGlyZWN0aW9uOiByb3c7XG4gICAgYWxpZ24taXRlbXM6IGNlbnRlcjtcbiAgICBmbGV4LWZsb3c6IGNvbHVtbjtcbn1cblxuXG4gIC5yZWdpc3RyYXRpb25Gb3Jte1xuICAgIG1hcmdpbi10b3A6IDQwcHggIWltcG9ydGFudDtcbiAgfVxuXG5cbi5idG5Mb2dpbiB7XG53aWR0aDo1MCU7XG59XG5cbi5idG5DbGVhciB7XG4gICAgbWFyZ2luLWxlZnQ6IDIwcHggIWltcG9ydGFudDtcbiAgICB3aWR0aDo0MCU7XG59XG5cbi5kaXZCdXR0b25ze1xuICAgIHRleHQtYWxpZ246IGNlbnRlcjtcbiAgICBtYXJnaW4tdG9wOiA0MHB4O1xufVxuXG4uZGl2Rm9ybUZpZWxkcyB7XG4gICAgbWFyZ2luLXRvcDo1JTtcbn1cblxubWF0LWZvcm0tZmllbGQge1xuICAgIHdpZHRoOiAxMDAlO1xuICAgIG1hcmdpbi1sZWZ0OiA1cHg7XG4gICAgbWFyZ2luLXJpZ2h0OiA1cHg7XG59XG5cbi5jdXN0b20tZXJyb3J7XG4gICAgZGlzcGxheTogYmxvY2s7XG4gICAgY29sb3I6ICNmNDQzMzY7XG59XG5cbi5sYmxDb25maXJtRXJyb3Ige1xuICAgIGZvbnQtc2l6ZTogMTBweDtcbn1cbiJdfQ== */");

/***/ }),

/***/ "./src/app/users/user-registration/user-registration.component.ts":
/*!************************************************************************!*\
  !*** ./src/app/users/user-registration/user-registration.component.ts ***!
  \************************************************************************/
/*! exports provided: UserRegistrationComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserRegistrationComponent", function() { return UserRegistrationComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var src_app_shared_roles__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/shared/roles */ "./src/app/shared/roles.ts");
/* harmony import */ var src_app_components_members_shared_member_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/components/members/shared/member.service */ "./src/app/components/members/shared/member.service.ts");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm2015/material.js");
/* harmony import */ var src_app_components_members_add_member_dialog_close_dialog_close_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! src/app/components/members/add-member-dialog-close/dialog-close.component */ "./src/app/components/members/add-member-dialog-close/dialog-close.component.ts");
/* harmony import */ var _user_model__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../user.model */ "./src/app/users/user.model.ts");
/* harmony import */ var _user_service__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ../user.service */ "./src/app/users/user.service.ts");









let UserRegistrationComponent = class UserRegistrationComponent {
    constructor(memberService, fb, dialog, dialogRef, userService) {
        this.memberService = memberService;
        this.fb = fb;
        this.dialog = dialog;
        this.dialogRef = dialogRef;
        this.userService = userService;
        this.members = [];
        this.roles = src_app_shared_roles__WEBPACK_IMPORTED_MODULE_3__["Roles"];
        this.email = '';
        this.phoneNo = '';
        this.user = new _user_model__WEBPACK_IMPORTED_MODULE_7__["UserModel"]();
        this.registrationForm = fb.group({
            user: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            email: new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormControl"]({ value: null, disabled: true }),
            phoneNo: new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormControl"]({ value: null, disabled: true }),
            password: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].minLength(5)]],
            confirmPassword: [null],
            roles: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required]
        });
    }
    ngOnInit() {
        this.getAllUserNames();
    }
    getAllUserNames() {
        this.memberService.getAllUserNames().subscribe((response) => {
            this.members = response;
        }, (error) => {
            console.log(error);
        }, () => {
            // To-Do
        });
    }
    onMemberSelected(event) {
        this.email = event.value.email;
        this.phoneNo = event.value.homePhoneNo;
    }
    onSave() {
        if (this.registrationForm.valid && this.comparePasswords()) {
            this.populateUser();
            this.userService.addUser(this.user).subscribe((result) => {
                console.log(result);
                this.dialogRef.close(null);
            }, (error) => {
                console.log(error);
            }, () => { });
        }
    }
    populateUser() {
        this.user.firstName = this.registrationForm.value.user.firstName;
        this.user.middleName = this.registrationForm.value.user.middleName;
        this.user.lastName = this.registrationForm.value.user.lastName;
        this.user.email = this.registrationForm.value.user.email;
        this.user.isActive = true;
        this.user.createdDate = new Date();
        this.user.updatedDate = new Date();
        this.user.phoneNo = this.registrationForm.value.user.homePhoneNo;
        this.user.roles = this.registrationForm.value.roles.toString().split(',');
        this.user.userId = 10;
    }
    onClear() {
        this.registrationForm.reset();
    }
    onClose() {
        if (this.registrationForm.dirty) {
            const dialogConfig = new _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialogConfig"]();
            dialogConfig.disableClose = true;
            dialogConfig.autoFocus = true;
            dialogConfig.width = '30%';
            const dialogRef = this.dialog.open(src_app_components_members_add_member_dialog_close_dialog_close_component__WEBPACK_IMPORTED_MODULE_6__["DialogCloseComponent"], dialogConfig);
            dialogRef.afterClosed().subscribe(result => {
                if (result === 'yes') {
                    this.dialogRef.close(null);
                }
            });
        }
        else {
            this.dialogRef.close(null);
        }
    }
    saveUserDisabled() {
        return !this.registrationForm.valid || !this.comparePasswords();
    }
    comparePasswords() {
        if (this.registrationForm.value.confirmPassword != null) {
            return this.registrationForm.value.password === this.registrationForm.value.confirmPassword;
        }
        return false;
    }
    showConfirmPasswordErrorMessage() {
        if (this.registrationForm != null &&
            this.registrationForm.dirty &&
            this.registrationForm.value.confirmPassword != null &&
            this.registrationForm.value.confirmPassword.toString().length > 0) {
            const password = this.registrationForm.value.password;
            const confirmPassword = this.registrationForm.value.confirmPassword;
            return password === confirmPassword ? false : true;
        }
        return false;
    }
};
UserRegistrationComponent.ctorParameters = () => [
    { type: src_app_components_members_shared_member_service__WEBPACK_IMPORTED_MODULE_4__["MemberService"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] },
    { type: _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialog"] },
    { type: _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialogRef"] },
    { type: _user_service__WEBPACK_IMPORTED_MODULE_8__["UserService"] }
];
UserRegistrationComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-user-registration',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./user-registration.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/users/user-registration/user-registration.component.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ./user-registration.component.css */ "./src/app/users/user-registration/user-registration.component.css")).default]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [src_app_components_members_shared_member_service__WEBPACK_IMPORTED_MODULE_4__["MemberService"],
        _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"],
        _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialog"],
        _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialogRef"],
        _user_service__WEBPACK_IMPORTED_MODULE_8__["UserService"]])
], UserRegistrationComponent);



/***/ }),

/***/ "./src/app/users/user.model.ts":
/*!*************************************!*\
  !*** ./src/app/users/user.model.ts ***!
  \*************************************/
/*! exports provided: UserModel */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserModel", function() { return UserModel; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");

class UserModel {
}


/***/ }),

/***/ "./src/app/users/user.service.ts":
/*!***************************************!*\
  !*** ./src/app/users/user.service.ts ***!
  \***************************************/
/*! exports provided: UserService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserService", function() { return UserService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var src_environments_environment__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! src/environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm2015/index.js");
/* harmony import */ var src_app_shared_roles__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/shared/roles */ "./src/app/shared/roles.ts");
/* harmony import */ var _shared_authentication_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../shared/authentication.service */ "./src/app/shared/authentication.service.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");







const usersList = [
    {
        userId: 1, firstName: 'test', middleName: 'test', lastName: 'user1', email: 'test.user1@test.com',
        roles: [src_app_shared_roles__WEBPACK_IMPORTED_MODULE_4__["Roles"].admin], phoneNo: '1234567890',
        createdDate: new Date('2020-01-01'), updatedDate: new Date('2020-01-01'), isActive: true
    },
    {
        userId: 2, firstName: 'test', middleName: 'test', lastName: 'user2', email: 'test.user2@test.com',
        roles: [src_app_shared_roles__WEBPACK_IMPORTED_MODULE_4__["Roles"].sebeka_gubae, src_app_shared_roles__WEBPACK_IMPORTED_MODULE_4__["Roles"].secretary], phoneNo: '1234567890',
        createdDate: new Date('2020-01-01'), updatedDate: new Date('2020-01-01'), isActive: true
    },
    {
        userId: 3, firstName: 'test', middleName: 'test', lastName: 'user3', email: 'test.user3@test.com',
        roles: [src_app_shared_roles__WEBPACK_IMPORTED_MODULE_4__["Roles"].secretary], phoneNo: '1234567890',
        createdDate: new Date('2020-01-01'), updatedDate: new Date('2020-01-01'), isActive: true
    },
    {
        userId: 4, firstName: 'test', middleName: 'test', lastName: 'user4', email: 'test.user4@test.com',
        roles: [src_app_shared_roles__WEBPACK_IMPORTED_MODULE_4__["Roles"].abo_wenber_sebeka_gubae], phoneNo: '1234567890',
        createdDate: new Date('2020-01-01'), updatedDate: new Date('2020-01-01'), isActive: true
    },
    {
        userId: 5, firstName: 'test', middleName: 'test', lastName: 'user5', email: 'test.user5@test.com',
        roles: [src_app_shared_roles__WEBPACK_IMPORTED_MODULE_4__["Roles"].sundaySchool], phoneNo: '1234567890',
        createdDate: new Date('2020-01-01'), updatedDate: new Date('2020-01-01'), isActive: false
    }
];
let UserService = class UserService {
    constructor(authService, httpClient) {
        this.authService = authService;
        this.httpClient = httpClient;
        this.usersList$ = Object(rxjs__WEBPACK_IMPORTED_MODULE_3__["of"])(usersList);
        this.baseUrl = src_environments_environment__WEBPACK_IMPORTED_MODULE_2__["environment"].apiUrl;
    }
    getUsers(showAll) {
        let userModelList = [];
        this.usersList$.subscribe((resp) => {
            if (resp != null) {
                if (showAll) {
                    userModelList = resp;
                }
                else {
                    resp.forEach(element => {
                        if (element.isActive) {
                            userModelList.push(element);
                        }
                    });
                }
            }
        });
        return Object(rxjs__WEBPACK_IMPORTED_MODULE_3__["of"])(userModelList);
        // this.httpClient.get<UserModel []>(this.url)
    }
    addUser(user) {
        // const headers = new HttpHeaders({
        //     'Content-Type': 'application/json',
        //     Authorization: localStorage.getItem('token')
        //   });
        // this.httpClient.post<number>(`${this.baseUrl}addUser`, {user}, {headers});
        usersList.push(user);
        return Object(rxjs__WEBPACK_IMPORTED_MODULE_3__["of"])(user.userId);
    }
};
UserService.ctorParameters = () => [
    { type: _shared_authentication_service__WEBPACK_IMPORTED_MODULE_5__["AuthenticationService"] },
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_6__["HttpClient"] }
];
UserService = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])({ providedIn: 'root' }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_shared_authentication_service__WEBPACK_IMPORTED_MODULE_5__["AuthenticationService"],
        _angular_common_http__WEBPACK_IMPORTED_MODULE_6__["HttpClient"]])
], UserService);



/***/ }),

/***/ "./src/app/users/users.component.css":
/*!*******************************************!*\
  !*** ./src/app/users/users.component.css ***!
  \*******************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".usersContainer {\n    padding: 0 1rem;\n}\n.search-form-field {\n    width: 50%;\n    margin-left: 26px;\n    padding: 0px 10px;\n    background-color: #f5f5f5;\n    border-radius: 5px;\n    max-height: 40px !important;\n}\nmat-header-cell {\n    font-weight: bold !important;\n    font-size: 15px;\n}\nmat-footer-row mat-footer-cell {\n    -webkit-box-pack: center;\n            justify-content: center;\n    font-style: italic;\n}\n.hide {\n    display: none;\n}\n.search-form-field div.mat-form-field-underline {\n    display: none;\n}\n.search-form-field div.mat-form-field-infix {\n    border-top: 0px;\n}\n.search-form-field div.mat-form-field-wrapper {\n    padding-bottom: 0px;\n}\n.search-form-field div.mat-form-field-suffix button{\n    height: 32px;\n    width: 32px;\n}\n.matTable {\n    width: 100%;\n}\nmat-header-cell {\n    display:-webkit-box;\n    display:flex;\n    -webkit-box-pack: left !important;\n            justify-content: left !important;\n    font-weight: bold;\n}\nmat-cell{\n    display:-webkit-box;\n    display:flex;\n    -webkit-box-pack: left !important;\n            justify-content: left !important;\n}\nmat-header-row {\n    min-height: 50px;\n    background-color: aliceblue;\n}\n.mat-row:nth-child(odd){\n    background-color:#f5f5f5;\n}\n#user_id {\n    max-width: 8%;\n    margin-left: 2px;\n    margin-right: 1px;\n}\n#fullName {\n    max-width: 16%;\n    margin-left: 1px;\n    margin-right: 1px;\n    word-break: break-word;\n}\n#email {\n    max-width: 16%;\n    margin-left: 1px;\n    margin-right: 1px;\n    word-break: break-word;\n}\n#user_mobile {\n    max-width: 7%;\n    margin-left: 1px;\n    margin-right: 3px;\n}\n#created_date {\n    max-width: 9%;\n    margin-left: 10px;\n    margin-right: 2px;\n}\n#updated_date {\n    max-width: 9%;\n    margin-left: 2px;\n    margin-right: 1px;\n}\n#roles {\n    max-width: 28%;\n    margin-left: 2px;\n    margin-right: 1px;\n    word-break: break-word;\n}\n#is_active {\n    max-width: 5%;\n    margin-left: 2px;\n}\n#user_actions {\n    max-width: 6%;\n    margin-right: 10px;\n    margin-left: 3px;\n}\n.showAllUsers {\n    padding-left: 25px;\n    padding-right: 35px;\n}\n#hr_user_header1 {\n    margin-top: 7px;\n    margin-bottom: 3px;\n}\n#hr_user_header2 {\n    margin-top: 1px;\n    margin-bottom: 5px;\n}\ndiv.search-div .mat-raised-button {\n    height: 40px;\n    bottom: 5px;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInVzZXJzL3VzZXJzLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7SUFDSSxlQUFlO0FBQ25CO0FBQ0E7SUFDSSxVQUFVO0lBQ1YsaUJBQWlCO0lBQ2pCLGlCQUFpQjtJQUNqQix5QkFBeUI7SUFDekIsa0JBQWtCO0lBQ2xCLDJCQUEyQjtBQUMvQjtBQUVBO0lBQ0ksNEJBQTRCO0lBQzVCLGVBQWU7QUFDbkI7QUFFQTtJQUNJLHdCQUF1QjtZQUF2Qix1QkFBdUI7SUFDdkIsa0JBQWtCO0FBQ3RCO0FBRUE7SUFDSSxhQUFhO0FBQ2pCO0FBRUE7SUFDSSxhQUFhO0FBQ2pCO0FBRUE7SUFDSSxlQUFlO0FBQ25CO0FBRUE7SUFDSSxtQkFBbUI7QUFDdkI7QUFFQTtJQUNJLFlBQVk7SUFDWixXQUFXO0FBQ2Y7QUFFQTtJQUNJLFdBQVc7QUFDZjtBQUVBO0lBQ0ksbUJBQVk7SUFBWixZQUFZO0lBQ1osaUNBQWdDO1lBQWhDLGdDQUFnQztJQUNoQyxpQkFBaUI7QUFDckI7QUFFQTtJQUNJLG1CQUFZO0lBQVosWUFBWTtJQUNaLGlDQUFnQztZQUFoQyxnQ0FBZ0M7QUFDcEM7QUFFQTtJQUNJLGdCQUFnQjtJQUNoQiwyQkFBMkI7QUFDL0I7QUFFQTtJQUNJLHdCQUF3QjtBQUM1QjtBQUVBO0lBQ0ksYUFBYTtJQUNiLGdCQUFnQjtJQUNoQixpQkFBaUI7QUFDckI7QUFFQTtJQUNJLGNBQWM7SUFDZCxnQkFBZ0I7SUFDaEIsaUJBQWlCO0lBQ2pCLHNCQUFzQjtBQUMxQjtBQUVBO0lBQ0ksY0FBYztJQUNkLGdCQUFnQjtJQUNoQixpQkFBaUI7SUFDakIsc0JBQXNCO0FBQzFCO0FBRUE7SUFDSSxhQUFhO0lBQ2IsZ0JBQWdCO0lBQ2hCLGlCQUFpQjtBQUNyQjtBQUVBO0lBQ0ksYUFBYTtJQUNiLGlCQUFpQjtJQUNqQixpQkFBaUI7QUFDckI7QUFFQTtJQUNJLGFBQWE7SUFDYixnQkFBZ0I7SUFDaEIsaUJBQWlCO0FBQ3JCO0FBRUE7SUFDSSxjQUFjO0lBQ2QsZ0JBQWdCO0lBQ2hCLGlCQUFpQjtJQUNqQixzQkFBc0I7QUFDMUI7QUFFQTtJQUNJLGFBQWE7SUFDYixnQkFBZ0I7QUFDcEI7QUFFQTtJQUNJLGFBQWE7SUFDYixrQkFBa0I7SUFDbEIsZ0JBQWdCO0FBQ3BCO0FBRUE7SUFDSSxrQkFBa0I7SUFDbEIsbUJBQW1CO0FBQ3ZCO0FBR0E7SUFDSSxlQUFlO0lBQ2Ysa0JBQWtCO0FBQ3RCO0FBRUE7SUFDSSxlQUFlO0lBQ2Ysa0JBQWtCO0FBQ3RCO0FBRUE7SUFDSSxZQUFZO0lBQ1osV0FBVztBQUNmIiwiZmlsZSI6InVzZXJzL3VzZXJzLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIudXNlcnNDb250YWluZXIge1xuICAgIHBhZGRpbmc6IDAgMXJlbTtcbn1cbi5zZWFyY2gtZm9ybS1maWVsZCB7XG4gICAgd2lkdGg6IDUwJTtcbiAgICBtYXJnaW4tbGVmdDogMjZweDtcbiAgICBwYWRkaW5nOiAwcHggMTBweDtcbiAgICBiYWNrZ3JvdW5kLWNvbG9yOiAjZjVmNWY1O1xuICAgIGJvcmRlci1yYWRpdXM6IDVweDtcbiAgICBtYXgtaGVpZ2h0OiA0MHB4ICFpbXBvcnRhbnQ7XG59XG5cbm1hdC1oZWFkZXItY2VsbCB7XG4gICAgZm9udC13ZWlnaHQ6IGJvbGQgIWltcG9ydGFudDtcbiAgICBmb250LXNpemU6IDE1cHg7XG59XG5cbm1hdC1mb290ZXItcm93IG1hdC1mb290ZXItY2VsbCB7XG4gICAganVzdGlmeS1jb250ZW50OiBjZW50ZXI7XG4gICAgZm9udC1zdHlsZTogaXRhbGljO1xufVxuXG4uaGlkZSB7XG4gICAgZGlzcGxheTogbm9uZTtcbn1cblxuLnNlYXJjaC1mb3JtLWZpZWxkIGRpdi5tYXQtZm9ybS1maWVsZC11bmRlcmxpbmUge1xuICAgIGRpc3BsYXk6IG5vbmU7XG59XG5cbi5zZWFyY2gtZm9ybS1maWVsZCBkaXYubWF0LWZvcm0tZmllbGQtaW5maXgge1xuICAgIGJvcmRlci10b3A6IDBweDtcbn1cblxuLnNlYXJjaC1mb3JtLWZpZWxkIGRpdi5tYXQtZm9ybS1maWVsZC13cmFwcGVyIHtcbiAgICBwYWRkaW5nLWJvdHRvbTogMHB4O1xufSBcblxuLnNlYXJjaC1mb3JtLWZpZWxkIGRpdi5tYXQtZm9ybS1maWVsZC1zdWZmaXggYnV0dG9ue1xuICAgIGhlaWdodDogMzJweDtcbiAgICB3aWR0aDogMzJweDtcbn1cblxuLm1hdFRhYmxlIHtcbiAgICB3aWR0aDogMTAwJTtcbn1cblxubWF0LWhlYWRlci1jZWxsIHtcbiAgICBkaXNwbGF5OmZsZXg7XG4gICAganVzdGlmeS1jb250ZW50OiBsZWZ0ICFpbXBvcnRhbnQ7XG4gICAgZm9udC13ZWlnaHQ6IGJvbGQ7XG59XG5cbm1hdC1jZWxse1xuICAgIGRpc3BsYXk6ZmxleDtcbiAgICBqdXN0aWZ5LWNvbnRlbnQ6IGxlZnQgIWltcG9ydGFudDtcbn1cblxubWF0LWhlYWRlci1yb3cge1xuICAgIG1pbi1oZWlnaHQ6IDUwcHg7XG4gICAgYmFja2dyb3VuZC1jb2xvcjogYWxpY2VibHVlO1xufVxuXG4ubWF0LXJvdzpudGgtY2hpbGQob2RkKXtcbiAgICBiYWNrZ3JvdW5kLWNvbG9yOiNmNWY1ZjU7XG59XG5cbiN1c2VyX2lkIHtcbiAgICBtYXgtd2lkdGg6IDglO1xuICAgIG1hcmdpbi1sZWZ0OiAycHg7XG4gICAgbWFyZ2luLXJpZ2h0OiAxcHg7XG59XG5cbiNmdWxsTmFtZSB7XG4gICAgbWF4LXdpZHRoOiAxNiU7XG4gICAgbWFyZ2luLWxlZnQ6IDFweDtcbiAgICBtYXJnaW4tcmlnaHQ6IDFweDtcbiAgICB3b3JkLWJyZWFrOiBicmVhay13b3JkO1xufVxuXG4jZW1haWwge1xuICAgIG1heC13aWR0aDogMTYlO1xuICAgIG1hcmdpbi1sZWZ0OiAxcHg7XG4gICAgbWFyZ2luLXJpZ2h0OiAxcHg7XG4gICAgd29yZC1icmVhazogYnJlYWstd29yZDtcbn1cblxuI3VzZXJfbW9iaWxlIHtcbiAgICBtYXgtd2lkdGg6IDclO1xuICAgIG1hcmdpbi1sZWZ0OiAxcHg7XG4gICAgbWFyZ2luLXJpZ2h0OiAzcHg7XG59XG5cbiNjcmVhdGVkX2RhdGUge1xuICAgIG1heC13aWR0aDogOSU7XG4gICAgbWFyZ2luLWxlZnQ6IDEwcHg7XG4gICAgbWFyZ2luLXJpZ2h0OiAycHg7XG59XG5cbiN1cGRhdGVkX2RhdGUge1xuICAgIG1heC13aWR0aDogOSU7XG4gICAgbWFyZ2luLWxlZnQ6IDJweDtcbiAgICBtYXJnaW4tcmlnaHQ6IDFweDtcbn1cblxuI3JvbGVzIHtcbiAgICBtYXgtd2lkdGg6IDI4JTtcbiAgICBtYXJnaW4tbGVmdDogMnB4O1xuICAgIG1hcmdpbi1yaWdodDogMXB4O1xuICAgIHdvcmQtYnJlYWs6IGJyZWFrLXdvcmQ7XG59XG5cbiNpc19hY3RpdmUge1xuICAgIG1heC13aWR0aDogNSU7XG4gICAgbWFyZ2luLWxlZnQ6IDJweDtcbn1cblxuI3VzZXJfYWN0aW9ucyB7XG4gICAgbWF4LXdpZHRoOiA2JTtcbiAgICBtYXJnaW4tcmlnaHQ6IDEwcHg7XG4gICAgbWFyZ2luLWxlZnQ6IDNweDtcbn1cblxuLnNob3dBbGxVc2VycyB7XG4gICAgcGFkZGluZy1sZWZ0OiAyNXB4O1xuICAgIHBhZGRpbmctcmlnaHQ6IDM1cHg7XG59XG5cblxuI2hyX3VzZXJfaGVhZGVyMSB7XG4gICAgbWFyZ2luLXRvcDogN3B4O1xuICAgIG1hcmdpbi1ib3R0b206IDNweDtcbn1cblxuI2hyX3VzZXJfaGVhZGVyMiB7XG4gICAgbWFyZ2luLXRvcDogMXB4O1xuICAgIG1hcmdpbi1ib3R0b206IDVweDtcbn1cblxuZGl2LnNlYXJjaC1kaXYgLm1hdC1yYWlzZWQtYnV0dG9uIHtcbiAgICBoZWlnaHQ6IDQwcHg7XG4gICAgYm90dG9tOiA1cHg7XG59Il19 */");

/***/ }),

/***/ "./src/app/users/users.component.ts":
/*!******************************************!*\
  !*** ./src/app/users/users.component.ts ***!
  \******************************************/
/*! exports provided: UsersComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsersComponent", function() { return UsersComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _user_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./user.service */ "./src/app/users/user.service.ts");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm2015/material.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm2015/common.js");
/* harmony import */ var _angular_cdk_collections__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/cdk/collections */ "./node_modules/@angular/cdk/esm2015/collections.js");
/* harmony import */ var _user_registration_user_registration_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./user-registration/user-registration.component */ "./src/app/users/user-registration/user-registration.component.ts");
/* harmony import */ var _user_edit_user_edit_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./user-edit/user-edit.component */ "./src/app/users/user-edit/user-edit.component.ts");








let UsersComponent = class UsersComponent {
    constructor(userService, dialog, datePipe) {
        this.userService = userService;
        this.dialog = dialog;
        this.datePipe = datePipe;
        this.subscriptions = [];
        this.selection = new _angular_cdk_collections__WEBPACK_IMPORTED_MODULE_5__["SelectionModel"](true, []);
        this.userListData = new _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatTableDataSource"]();
        this.showAll = false;
        this.displayedColumns = ['userId', 'fullName', 'email', 'phoneNo', 'createdDate', 'updatedDate', 'roles', 'isActive', 'actions'];
        this.isLoading = false;
    }
    ngOnInit() {
        this.getUserList();
        this.userListData.paginator = this.paginator;
    }
    getUserList() {
        this.isLoading = true;
        setTimeout(() => {
            this.subscriptions.push(this.userService.getUsers(this.showAll).subscribe(response => {
                if (response != null) {
                    this.userListData.data = response;
                    this.userList = response;
                }
                else {
                    this.userList = [];
                    this.userListData.data = [];
                }
            }, (error) => {
                this.isLoading = false;
                console.log(error);
            }, () => {
                this.isLoading = false;
            }));
        }, 1000);
    }
    toShortDate(value) {
        return this.datePipe.transform(value, 'MM-dd-yyyy');
    }
    onSearchClear() {
        this.searchKey = '';
        this.applyFilter();
    }
    applyFilter() {
        this.userListData.filter = this.searchKey.trim().toLowerCase();
    }
    showAllUsers() {
        this.getUserList();
    }
    getRoles(user) {
        return user.roles;
    }
    onAddUser() {
        this.onSearchClear();
        this.showAll = false;
        this.getUserList();
        const dialogConfig = new _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatDialogConfig"]();
        dialogConfig.autoFocus = true;
        dialogConfig.disableClose = true;
        dialogConfig.width = '40%';
        dialogConfig.height = '69%';
        const dialogRef = this.dialog.open(_user_registration_user_registration_component__WEBPACK_IMPORTED_MODULE_6__["UserRegistrationComponent"], dialogConfig);
        this.subscriptions.push(dialogRef.afterClosed().subscribe(() => {
            this.getUserList();
        }));
    }
    onEdit(user) {
        console.log(user);
        const dialogConfig = new _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatDialogConfig"]();
        dialogConfig.autoFocus = true;
        dialogConfig.disableClose = true;
        dialogConfig.width = '40%';
        dialogConfig.height = '69%';
        dialogConfig.data = user;
        const dialogRef = this.dialog.open(_user_edit_user_edit_component__WEBPACK_IMPORTED_MODULE_7__["UserEditComponent"], dialogConfig);
        this.subscriptions.push(dialogRef.afterClosed().subscribe(() => {
            this.getUserList();
        }));
    }
    ngOnDestroy() {
        this.subscriptions.forEach(sub => {
            sub.unsubscribe();
        });
    }
};
UsersComponent.ctorParameters = () => [
    { type: _user_service__WEBPACK_IMPORTED_MODULE_2__["UserService"] },
    { type: _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatDialog"] },
    { type: _angular_common__WEBPACK_IMPORTED_MODULE_4__["DatePipe"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_3__["MatSort"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatSort"])
], UsersComponent.prototype, "sort", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_3__["MatPaginator"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatPaginator"])
], UsersComponent.prototype, "paginator", void 0);
UsersComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-users',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./users.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/users/users.component.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ./users.component.css */ "./src/app/users/users.component.css")).default]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_user_service__WEBPACK_IMPORTED_MODULE_2__["UserService"],
        _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatDialog"],
        _angular_common__WEBPACK_IMPORTED_MODULE_4__["DatePipe"]])
], UsersComponent);



/***/ }),

/***/ "./src/app/users/users.module.ts":
/*!***************************************!*\
  !*** ./src/app/users/users.module.ts ***!
  \***************************************/
/*! exports provided: UsersModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsersModule", function() { return UsersModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _users_routing__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./users.routing */ "./src/app/users/users.routing.ts");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm2015/common.js");
/* harmony import */ var _shared_shared_module__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../shared/shared.module */ "./src/app/shared/shared.module.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _user_registration_user_registration_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./user-registration/user-registration.component */ "./src/app/users/user-registration/user-registration.component.ts");
/* harmony import */ var _user_edit_user_edit_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./user-edit/user-edit.component */ "./src/app/users/user-edit/user-edit.component.ts");








let UsersModule = class UsersModule {
};
UsersModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
        declarations: [
            _users_routing__WEBPACK_IMPORTED_MODULE_2__["UsersRouting"].components,
            _user_edit_user_edit_component__WEBPACK_IMPORTED_MODULE_7__["UserEditComponent"]
        ],
        imports: [
            _users_routing__WEBPACK_IMPORTED_MODULE_2__["UsersRouting"],
            _angular_common__WEBPACK_IMPORTED_MODULE_3__["CommonModule"],
            _shared_shared_module__WEBPACK_IMPORTED_MODULE_4__["SharedModule"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_5__["ReactiveFormsModule"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_5__["FormsModule"]
        ],
        entryComponents: [
            _user_registration_user_registration_component__WEBPACK_IMPORTED_MODULE_6__["UserRegistrationComponent"],
            _user_edit_user_edit_component__WEBPACK_IMPORTED_MODULE_7__["UserEditComponent"]
        ]
    })
], UsersModule);



/***/ }),

/***/ "./src/app/users/users.routing.ts":
/*!****************************************!*\
  !*** ./src/app/users/users.routing.ts ***!
  \****************************************/
/*! exports provided: UsersRouting */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsersRouting", function() { return UsersRouting; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _users_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./users.component */ "./src/app/users/users.component.ts");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _user_registration_user_registration_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./user-registration/user-registration.component */ "./src/app/users/user-registration/user-registration.component.ts");





const routes = [
    { path: '', component: _users_component__WEBPACK_IMPORTED_MODULE_2__["UsersComponent"] }
];
let UsersRouting = class UsersRouting {
};
UsersRouting.components = [_users_component__WEBPACK_IMPORTED_MODULE_2__["UsersComponent"], _user_registration_user_registration_component__WEBPACK_IMPORTED_MODULE_4__["UserRegistrationComponent"]];
UsersRouting = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_3__["NgModule"])({
        imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
        exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
    })
], UsersRouting);



/***/ })

}]);
//# sourceMappingURL=users-users-module-es2015.js.map