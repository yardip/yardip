/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.security.dao;

/**
 *
 * @author aphasan
 */
//@ApplicationScoped
public class SecurityTokenIdentityStore /*implements RememberMeIdentityStore*/ {

//    @Inject
//    private ApplicationUserFacade userService;
//
//    @Inject
//    private SecurityTokenFacade tokenService;
//
//    @Override
//    public CredentialValidationResult validate(RememberMeCredential credential) {
//        
//        Optional<SecurityToken> token = Optional.ofNullable(
//                tokenService.find(credential.getToken()));
//
//        if (token.isPresent()) {
//            Person party = token.get().getPerson();
//
//            Optional<ApplicationUser> user = Optional.ofNullable(
//                    userService.findByPerson(party));
//
//            if (user.isPresent()) {
//                return new CredentialValidationResult(
//                        new UserPrincipal(user.get()),
//                        userService.getGroupsAsString(user.get()));
//            }
//        }
//
//        return CredentialValidationResult.INVALID_RESULT;
//
//    }
//
//    @Override
//    public String generateLoginToken(CallerPrincipal callerPrincipal,
//            Set<String> groups) {
//        
//        Optional<ApplicationUser> user = Optional.ofNullable(
//                ((UserPrincipal) callerPrincipal).getUser());
//
//        if (user.isPresent()) {
//
//            SecurityToken securityToken = tokenService.findByUser(user.get());
//
//            Optional<SecurityToken> token = Optional.ofNullable(securityToken);
//
//            if (token.isPresent()) {
//                return token.get().getId();
//            } else {
//                String tokenId = UUID.nameUUIDFromBytes(
//                        (UUID.randomUUID().toString() + "-"
//                                + user.get().getId()).getBytes()
//                ).toString();
//
//                securityToken = new SecurityToken();
//                securityToken.setId(tokenId);
//                securityToken.setPerson((Person) user.get().getParty());
//                tokenService.create(securityToken);
//
//                return tokenId;
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public void removeLoginToken(String token) {
//        Optional<SecurityToken> existingToken = Optional.ofNullable(
//                tokenService.find(token));
//        if (existingToken.isPresent()) {
//            tokenService.remove(existingToken.get());
//        }
//    }
}
