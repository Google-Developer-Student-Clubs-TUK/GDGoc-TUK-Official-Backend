package gdgoc.tuk.official.member.dto;

import java.util.List;

public record MemberListResponse(
    List<MemberResponse> memberList
) {}
